---
layout: .components.layouts.BlogLayout
title: "[가이드] 리니지 OS 16 빌드하기 for 팬택 MSM8974"
date: "2019-12-29"
description: ""
tags: ["build-guide", "msm8974", "pantech"]
---

# [가이드] 리니지 OS 16 빌드하기 for 팬택 MSM8974

- 리니지 OS 16 빌드하기 for 팬택 MSM8974 -

본 글은 빌드환경 구축이 이미 완료되었다는 가정하에 작성된 가이드입니다.

만약 빌드환경이 구축되지 않았다면 [이 주소](https://source.android.com/setup/build/initializing)에서 빌드환경을 구축하고 오세요.

또한, SSD가 있다면 SSD에서 빌드하시는걸 강력하게 추천합니다.

![](/imported/blog/17/01.png)

먼저 소스코드를 다운받을 디렉토리를 만들어줍니다.

```
mkdir -p lineage/16
```

![](/imported/blog/17/02.png)

만든 디렉토리로 이동합니다.

```
cd lineage/16
```

![](/imported/blog/17/03.png)

![](/imported/blog/17/04.png)

repo를 설정해줍니다.

```
repo init -u git://github.com/LineageOS/android.git -b lineage-16.0
```

![](/imported/blog/17/05.png)

repo를 설정하였으니, 팬택 MSM8974 소스코드의 git 저장소 정보를 가져와야 합니다.

직접 소스코드를 받는 방법도 있지만 이 방법이 조금 더 간편합니다.

```
mkdir .repo/local_manifests && cd .repo/local_manifests && wget https://raw.githubusercontent.com/kongwoojin/manifest_sky/lineage-16.0/roomservice.xml && cd ../../
```

![](/imported/blog/17/06.png)

repo 설정이 끝났다면, 소스코드를 받아줘야 합니다.

-j 옵션은 컴퓨터 성능에 맞게 설정해주시면 됩니다.

다운로드에 시간이 좀 걸리니 느긋하게 기다리세요.

```
repo sync  -jX
```

![](/imported/blog/17/07.png)

소스코드 다운로드가 완료되었습니다.

제 기준으로 약 20분 정도 소요되네요.

![](/imported/blog/17/08.png)

본격적인 빌드를 시작하겠습니다.

먼저 아래의 명령어를 입력해주세요.

```
source build/envsetup.sh
```

또는 아래의 명령어도 가능합니다.

```
. build/envsetup.sh
```

![](/imported/blog/17/09.png)

먼저 breakfast 명령어를 사용합니다.

만약 베가 아이언2를 빌드하고자 한다면 아래 명령어를 입력하세요

```
breakfast ef63
```

베가 팝업노트를 빌드하고자 한다면 아래 명령어를 입력하세요

```
breakfast ef65
```

베가 시크릿 노트를 빌드하고자 한다면 아래 명령어를 입력하세요

```
breakfast ef59
```

![](/imported/blog/17/10.png)

CCACHE를 활성화 시켜줍니다.

CCACHE를 사용하지 않는다고 빌드에 문제가 생기진 않지만, 빠른 속도를 위해서 설정해주세요.

```
export USE_CCACHE=1
```

또한, CCACHE의 용량을 설정해줍니다.

일반적으로 50G정도 설정합니다.

```
ccache -M 50G
```

![](/imported/blog/17/11.png)

자 이제 빌드를 시작합니다.

베가 아이언2를 빌드하고자 한다면 아래 명령어를 입력해주세요.

```
brunch ef63
```

베가 팝업노트를 빌드하고자 한다면 아래 명령어를 입력해주세요.

```
brunch ef65
```

베가 시크릿 노트를 빌드하고자 한다면 아래 명령어를 입력해주세요.

```
brunch ef59
```

이제 빌드가 끝날때까지 기다리면 됩니다.

## sepolicy 오류 발생시 해결법 ##

![](/imported/blog/17/12.png)

위와 같이 sepolicy 오류 발생시 해결법입니다.

![](/imported/blog/17/13.png)

먼저 device/pantech/msm8974-common 경로에서 sepolicy 폴더를 찾아줍니다.

![](/imported/blog/17/14.png)

sepolicy 폴더 안에 webview\_zygote.te를 수정해야 합니다.

![](/imported/blog/17/15.png)

마지막 줄인 allow webview\_zygote  theme\_data\_file:dir search;를 지워줍니다.

![](/imported/blog/17/16.png)

![](/imported/blog/17/17.png)

다시 빌드를 시작합니다.

![](/imported/blog/17/18.png)

빌드가 정상적으로 끝났습니다.

---

원본 게시글: https://blog.kongjak.dev/17
