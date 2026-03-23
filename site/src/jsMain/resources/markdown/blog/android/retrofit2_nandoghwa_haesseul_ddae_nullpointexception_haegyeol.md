---
layout: .components.layouts.BlogLayout
title: "Retrofit2 난독화 했을 때 NullPointException 해결"
date: "2022-10-02"
description: ""
tags: ["android", "retrofit", "proguard"]
---

# Retrofit2 난독화 했을 때 NullPointException 해결

Retrofit2를 사용하는 프로젝트를 개발하던 중, Proguard를 적용하여 난독화를 진행하면 NPE가 발생하는 문제를 직면했다.

난독화가 적용되지 않은 빌드에서는 발생하지 않던 문제였기에, 난독화 과정에서 발생한 문제임을 유추할 수 있었다.

Retrofit에서는 JSON의 키 값을 Java의 POJO에 맞게 매핑하기 위해 @SerializedName 어노테이션을 추가한다.

나 같은 경우 JSON의 키 값과 Kotlin의 data class의 변수 이름이 다른 경우가 있어,  통일성을 위해 모든 값에 @SerializedName을 붙여둔 부분이 문제가 되었다.

난독화 과정에서 @SerializedName 또한 난독화가 진행되었고, 데이터를 정상적으로 받아오지 못하게 되면서 NPE가 발생한 것이다.

이를 해결하기 위해, Proguard Rules에 아래와 같이 @SerializedName을 난독화 하지 않도록 규칙을 추가하였다.

```
# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}
```

---

원본 게시글: https://blog.kongjak.dev/29
