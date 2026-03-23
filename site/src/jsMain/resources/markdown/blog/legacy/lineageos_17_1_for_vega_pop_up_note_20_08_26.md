---
layout: .components.layouts.BlogLayout
title: "LineageOS 17.1 for Vega Pop-Up Note 20/08/26"
date: "2020-08-26"
description: ""
tags: ["lineageos", "vega-pop-up-note", "pantech"]
---

# LineageOS 17.1 for Vega Pop-Up Note 20/08/26

**본 자료를 사용함으로써 발생하는 모든 문제(하드브릭 등)에 대한 책임은 사용자 본인에게 있으며,**

**개발자는 책임이 없음을 알립니다.**

작동
- 와이파이
- 블루투스
- GPS
- 소리

-V-PEN

기타
- 5Ghz 와이파이는 속도가 매우 느립니다.

 

변경점
- V펜 활성화/비활성화 기능 추가
- V펜 터치모드 변경 기능 추가(필기모드, 그림모드)

**Thanks To**

LineageOS
chautruongthinh

hiru (rlawoehd187)

그 외 기타 개발자분들...

소스코드
[SKY VEGA DEV TEAM](https://github.com/sky-vega-dev-team)

다운로드
[dl.kongjak.com/ef65/LineageOS/lineage-17.1-20200826-UNOFFICIAL-ef65.zip](https://dl.kongjak.com/ef65/LineageOS/lineage-17.1-20200826-UNOFFICIAL-ef65.zip)

![](/imported/blog/27/01.png)![](/imported/blog/27/02.png)이전다음

01

커널소스에 V펜은 두가지 모드(TOUCH\_MODE\_LETTER, TOUCH\_MODE\_DRAW) 가 있습니다.

기존의 VPenDetect는 TOUCH\_MODE\_LETTER(필기 모드)로 고정이 되어있었으나 이번 버전에는 TOUCH\_MODE\_DRAW(그림 모드)로 변경할 수 있는 기능이 추가되었습니다.

두 모드간의 큰 차이는 없어보이나, 순정펌웨어에서 두 모드 모두 사용되었던것으로 기억하여 변경할 수 있도록 수정하였으며, 주의하실 점은 그림 모드로 사용시 하단키가 펜을 인식하지 못합니다.

특별한 이유가 있지 않는 한 필기 모드 사용을 권장합니다.

---

원본 게시글: https://blog.kongjak.dev/27
