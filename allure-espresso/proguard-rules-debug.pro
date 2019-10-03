-include proguard-rules.pro

-dontoptimize
-dontshrink
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose

-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable