from __future__ import annotations

import copy
import re
import sys
from datetime import datetime
from pathlib import Path
from typing import Iterable
from urllib.parse import urljoin, urlparse

import requests
from bs4 import BeautifulSoup, Tag

sys.path.insert(0, "/tmp/kongjak-blog-import")
from markdownify import markdownify as md  # type: ignore
from slugify import slugify  # type: ignore


ROOT = Path(__file__).resolve().parents[1]
MARKDOWN_ROOT = ROOT / "site" / "src" / "jsMain" / "resources" / "markdown" / "blog"
PUBLIC_ROOT = ROOT / "site" / "src" / "jsMain" / "resources" / "public" / "imported" / "blog"
BASE_URL = "https://blog.kongjak.dev"
POST_IDS = list(range(3, 34))

CATEGORY_PATHS = {
    "레거시": "legacy",
    "회고": "retrospective",
    "개발/안드로이드": "android",
}

SESSION = requests.Session()
SESSION.headers.update(
    {
        "User-Agent": "Mozilla/5.0 (compatible; KongjakBlogImporter/1.0; +https://kongjak.com)",
    }
)


def fetch(url: str) -> str:
    response = SESSION.get(url, timeout=30)
    response.raise_for_status()
    return response.text


def fetch_bytes(url: str) -> bytes:
    response = SESSION.get(url, timeout=60)
    response.raise_for_status()
    return response.content


def escape_yaml(value: str) -> str:
    return value.replace("\\", "\\\\").replace('"', '\\"')


def clean_text(value: str) -> str:
    return re.sub(r"\s+", " ", value.replace("\xa0", " ")).strip()


def infer_tags(title: str, description: str, category_path: str) -> list[str]:
    haystack = title.lower()
    tags: list[str] = []

    def add(*values: str) -> None:
        for value in values:
            if value not in tags:
                tags.append(value)

    if category_path == "android":
        add("android")
    if category_path == "retrospective":
        add("retrospective")

    keyword_map = [
        (["compose", "contentdescription"], ["compose", "jetpack-compose"]),
        (["retrofit"], ["retrofit", "android", "proguard"]),
        (["lineageos 16"], ["lineageos"]),
        (["lineageos 17.0"], ["lineageos"]),
        (["lineageos 17.1"], ["lineageos"]),
        (["pixel experience", "pixelexperience"], ["pixel-experience"]),
        (["crdroid"], ["crdroid"]),
        (["twrp"], ["twrp", "recovery"]),
        (["build", "빌드하기"], ["build-guide"]),
        (["bootstrap"], ["frontend", "template"]),
        (["archive"], ["archive"]),
        (["jetpack compose", "마이그레이션"], ["jetpack-compose", "migration", "android"]),
        (["vega pop-up note", "vega pop up note", "팝업노트"], ["vega-pop-up-note", "pantech"]),
        (["vega iron 2", "아이언2"], ["vega-iron-2", "pantech"]),
        (["msm8974"], ["msm8974", "pantech"]),
        (["팬택", "pantech"], ["pantech"]),
    ]

    for needles, values in keyword_map:
        if any(needle in haystack for needle in needles):
            add(*values)

    return tags[:6]


def extract_device(title: str) -> str | None:
    lowered = title.lower()
    if "vega pop-up note" in lowered or "vega pop up note" in lowered:
        return "Vega Pop-Up Note"
    if "vega iron 2" in lowered:
        return "Vega Iron 2"
    if "msm8974" in lowered:
        return "Pantech MSM8974"
    return None


def is_custom_rom_post(title: str) -> bool:
    lowered = title.lower()
    return any(keyword in lowered for keyword in ["lineageos", "pixelexperience", "crdroid"])


def first_meaningful_paragraph(markdown: str) -> str:
    for block in markdown.split("\n\n"):
        line = block.strip()
        if not line:
            continue
        if line.startswith("#"):
            continue
        if line.startswith("![]("):
            continue
        if line.startswith("```"):
            continue
        line = re.sub(r"\[(.*?)\]\((.*?)\)", r"\1", line)
        line = re.sub(r"[#>*`_]", "", line)
        line = clean_text(line)
        if len(line) >= 20:
            return line
    return ""


def first_sentence(text: str) -> str:
    cleaned = clean_text(text)
    if not cleaned:
        return ""
    match = re.search(r"^(.+?[.!?]|.+?다\\.|.+?니다\\.|.+?입니다\\.)($|\\s)", cleaned)
    sentence = match.group(1) if match else cleaned
    if len(sentence) > 110:
        sentence = sentence[:107].rstrip() + "..."
    return sentence


def infer_description(title: str, markdown: str, tags: list[str], category_path: str) -> str:
    title_clean = clean_text(title)
    lowered = title_clean.lower()
    device = extract_device(title_clean)
    first_paragraph = first_meaningful_paragraph(markdown)

    if is_custom_rom_post(title_clean):
        return ""
    if "contentdescription" in lowered:
        return first_sentence(first_paragraph)
    if "retrofit" in lowered and "proguard" in lowered or "nullpointexception" in lowered:
        return first_sentence(first_paragraph)
    if "jetpack compose" in lowered and "마이그레이션" in title_clean:
        return first_sentence(first_paragraph)
    if "bootstrap" in lowered:
        return first_sentence(first_paragraph)
    if "archive" in lowered:
        return first_sentence(first_paragraph)
    if "twrp" in lowered:
        return first_sentence(first_paragraph)
    if "빌드하기" in title_clean or "build-guide" in tags:
        return first_sentence(first_paragraph)

    if first_paragraph:
        return first_sentence(first_paragraph)

    return f"{title_clean}에 대한 내용을 정리한 글입니다."


def parse_post(post_id: int) -> dict[str, str | list[str]]:
    soup = BeautifulSoup(fetch(f"{BASE_URL}/{post_id}"), "html.parser")
    title = soup.select_one('meta[property="og:title"]')
    description = soup.select_one('meta[property="og:description"]')
    published = soup.select_one('meta[property="article:published_time"]')
    category = soup.select_one("a.category")
    content = soup.select_one(".contents_style")

    if not title or not published or not content:
        raise RuntimeError(f"Failed to parse required fields for post {post_id}")

    title_text = clean_text(title.get("content", ""))
    description_text = clean_text(description.get("content", "")) if description else ""
    published_at = datetime.fromisoformat(published.get("content", "")).date().isoformat()
    category_label = clean_text(category.get_text(" ", strip=True)) if category else "레거시"
    category_path = CATEGORY_PATHS.get(category_label)
    if not category_path:
        raise RuntimeError(f"Unsupported category mapping for post {post_id}: {category_label}")
    tags = infer_tags(title_text, description_text, category_path)

    content_copy = copy.copy(content)
    content_copy.attrs = {}

    for bad in content_copy.select("script, style, noscript, iframe"):
        bad.decompose()

    image_links = download_images(post_id, content_copy.select("img"))
    markdown = md(
        str(content_copy),
        heading_style="ATX",
        bullets="-",
        strip=["button"],
    )
    markdown = normalize_markdown(markdown, title_text)
    rewritten_description = infer_description(title_text, markdown, tags, category_path)

    return {
        "title": title_text,
        "description": rewritten_description,
        "date": published_at,
        "category_path": category_path,
        "tags": tags,
        "markdown": markdown,
        "images": image_links,
    }


def download_images(post_id: int, images: Iterable[Tag]) -> list[str]:
    saved = []
    post_image_dir = PUBLIC_ROOT / str(post_id)
    post_image_dir.mkdir(parents=True, exist_ok=True)

    for index, img in enumerate(images, start=1):
        source = (
            img.get("src")
            or img.get("data-src")
            or img.get("data-original")
            or img.get("data-ke-src")
            or img.get("srcset", "").split(" ", 1)[0]
        )
        if not source:
            continue
        source = urljoin(BASE_URL, source)
        parsed = urlparse(source)
        suffix = Path(parsed.path).suffix.lower()
        if not suffix or len(suffix) > 5:
            suffix = ".png"
        filename = f"{index:02d}{suffix}"
        file_path = post_image_dir / filename
        try:
            file_path.write_bytes(fetch_bytes(source))
        except requests.HTTPError:
            print(f"Warning: failed to download image for post {post_id}: {source}")
            continue
        public_path = f"/imported/blog/{post_id}/{filename}"
        img["src"] = public_path
        img.attrs = {"src": public_path, "alt": clean_text(img.get("alt", ""))}
        saved.append(public_path)

    return saved


def normalize_markdown(markdown: str, title: str) -> str:
    markdown = markdown.replace("\r\n", "\n")
    markdown = re.sub(r"\n{3,}", "\n\n", markdown).strip()
    markdown = re.sub(r"[ \t]+\n", "\n", markdown)
    markdown = re.sub(r"(?m)^ +```", "```", markdown)
    markdown = re.sub(r"(?m)^```+\s*\n```+", "```", markdown)
    if not markdown.startswith("# "):
        markdown = f"# {title}\n\n{markdown}".strip()
    return markdown + "\n"


def slugify_filename(title: str, post_id: int) -> str:
    slug = slugify(title, separator="_")
    slug = slug or f"post_{post_id}"
    if slug[0].isdigit():
        slug = f"post_{slug}"
    if slug == "index":
        slug = f"post_{post_id}"
    return slug


def write_markdown(post_id: int, post: dict[str, str | list[str]]) -> Path:
    relative_dir = Path(str(post["category_path"]))
    destination_dir = MARKDOWN_ROOT / relative_dir
    destination_dir.mkdir(parents=True, exist_ok=True)
    destination = destination_dir / f"{slugify_filename(str(post['title']), post_id)}.md"
    tags_line = ", ".join(f'"{escape_yaml(tag)}"' for tag in post["tags"])
    destination.write_text(
        (
            "---\n"
            "layout: .components.layouts.BlogLayout\n"
            f'title: "{escape_yaml(str(post["title"]))}"\n'
            f'date: "{post["date"]}"\n'
            f'description: "{escape_yaml(str(post["description"]))}"\n'
            f"tags: [{tags_line}]\n"
            "---\n\n"
            f'{post["markdown"]}'
        ),
        encoding="utf-8",
    )
    return destination


def main() -> None:
    imported = []
    for post_id in POST_IDS:
        post = parse_post(post_id)
        destination = write_markdown(post_id, post)
        imported.append(destination.relative_to(ROOT).as_posix())
        print(f"Imported {post_id} -> {destination.relative_to(ROOT)}")

    print(f"Imported {len(imported)} posts")


if __name__ == "__main__":
    main()
