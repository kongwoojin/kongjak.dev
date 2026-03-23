---
layout: .components.layouts.BlogLayout
title: "마크다운 블로그 시작"
date: "2026-03-23"
description: "Kobweb 사이트에 마크다운 기반 블로그를 붙이고, /blog 목록에서 자동으로 노출되도록 구성한 첫 글입니다."
tags: ["kobweb", "blog", "markdown"]
---

# 마크다운 블로그 시작

이제 이 사이트에서는 `site/src/jsMain/resources/markdown/blog` 아래에
마크다운 파일을 추가하면 게시글 페이지가 자동으로 생성됩니다.

또한 `/blog` 페이지는 빌드 시점에 마크다운의 front matter를 읽어서
게시글 제목, 날짜, 설명, 카테고리를 자동으로 목록에 반영합니다.

카테고리를 만들고 싶다면 하위 폴더를 사용하면 됩니다.
예를 들어 `blog/android/first_post.md`는 `/blog/android/first-post`에,
해당 카테고리 목록은 `/blog/android`에 나타납니다.

## 작성 규칙

- 파일 위치: `site/src/jsMain/resources/markdown/blog`
- 파일 확장자: `.md` 또는 `.markdown`
- 권장 파일명: `snake_case`
- 카테고리: 첫 번째 하위 폴더로 결정
- 태그: `tags`에 배열로 추가
- 권장 front matter: `title`, `date`, `description`, `tags`, `layout`

## 예시

```md
---
layout: .components.layouts.BlogLayout
title: "새 글 제목"
date: "2026-03-23"
description: "목록에 보여줄 짧은 설명"
---

# 새 글 제목

본문은 마크다운으로 작성합니다.
```
