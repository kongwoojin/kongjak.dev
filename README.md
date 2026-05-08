# kongjak.com

[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.20-7F52FF?logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Kobweb](https://img.shields.io/badge/Kobweb-0.23.3-orange)](https://kobweb.varabyte.com/)
[![Deploy](https://img.shields.io/github/actions/workflow/status/kongwoojin/kongjak.com/gh-pages.yml?label=deploy&logo=githubactions&logoColor=white)](https://github.com/kongwoojin/kongjak.com/actions)
[![Site](https://img.shields.io/website?url=https%3A%2F%2Fkongjak.dev&label=kongjak.dev)](https://kongjak.dev)

Source code of [kongjak.dev](https://kongjak.dev)

## 기술 스택

| 항목 | 내용 |
|------|------|
| 언어 | [Kotlin/JS](https://kotlinlang.org/docs/js-overview.html) 2.2.20 |
| 프레임워크 | [Kobweb](https://kobweb.varabyte.com/) 0.23.3 |
| UI | [Compose HTML](https://github.com/JetBrains/compose-multiplatform) (Kobweb Silk) |
| 빌드 | Gradle (Kotlin DSL) |
| 배포 | GitHub Pages (GitHub Actions) |

## 구조

```
site/src/jsMain/
├── kotlin/         # Kotlin/JS 소스 (페이지, 컴포넌트, 모델)
└── resources/
    └── markdown/   # 블로그 포스트 (.md)
        ├── android/
        ├── cs_study/
        ├── legacy/
        └── retrospective/

buildSrc/           # 빌드타임 코드 생성 (블로그 메타데이터, RSS, GitHub 기여도)
```

## 로컬 실행

```bash
kobweb run
```
