[![Build Status](https://github.com/allure-framework/allure-android/workflows/Build/badge.svg)](https://github.com/allure-framework/allure-android/actions)

[license]: http://www.apache.org/licenses/LICENSE-2.0 "Apache License 2.0"
[allure2]: https://github.com/allure-framework/allure2
[blog]: https://qameta.io/blog
[gitter]: https://gitter.im/allure-framework/allure-core
[gitter-ru]: https://gitter.im/allure-framework/allure-ru
[twitter]: https://twitter.com/QametaSoftware "Qameta Software"
[twitter-team]: https://twitter.com/QametaSoftware/lists/team/members "Team"
[CONTRIBUTING.md]: .github/CONTRIBUTING.md
[docs]: https://docs.qameta.io/allure/2.0/

# Allure Android
* [Source](https://github.com/allure-framework/allure-android)
* [Documentation][docs]
* [Gitter][gitter]

[Allure][allure2] adapter for Android, which is written on Kotlin.

## Installation and Usage

### Configure
#### Gradle

New version with support AndroidX (BETA), available [here](https://bintray.com/qameta/maven/allure-android/2.0.0-beta1)

Add to: _app/build.gradle_

```gradle
apply plugin: 'kotlin-android'

repositories {
    mavenCentral()
    maven { url 'https://dl.bintray.com/qameta/maven' }
}

dependencies {
    androidTestCompile "io.qameta.allure:allure-android-commons:2.0.0-beta1"
    androidTestCompile "io.qameta.allure:allure-android-model:2.0.0-beta1"
    androidTestCompile "io.qameta.allure:allure-espresso:2.0.0-beta1"

    androidTestCompile "org.jetbrains.kotlin:kotlin-stdlib:1.2.51"
    androidTestCompile "junit:junit:4.12"
    androidTestCompile "androidx.test.uiautomator:uiautomator:2.2.0"
}

android {
    defaultConfig {
        testInstrumentationRunner "io.qameta.allure.espresso.AllureAndroidRunner"
    }
}

```
ut be aware that new version isn't backward compatible with older versions. 

Old version, without AndroidX, now not supported
Add to: _app/build.gradle_

```gradle
apply plugin: 'kotlin-android'

dependencies {
    androidTestCompile "ru.tinkoff.allure:allure-android:2.5.1@aar"
    androidTestCompile "ru.tinkoff.allure:allure-common:2.5.1"
    androidTestCompile "ru.tinkoff.allure:allure-model:2.5.1"

    androidTestCompile "org.jetbrains.kotlin:kotlin-stdlib:1.1.51"
    androidTestCompile "junit:junit:4.12"
    androidTestCompile "com.android.support.test.uiautomator:uiautomator-v18:2.1.2"
}

android {
    defaultConfig {
        testInstrumentationRunner "ru.tinkoff.allure.android.AllureAndroidRunner"
    }
}

```

### Pull allure results
Just use comandline tools from Android sdk:
```shell
adb pull /sdcard/allure-results
```

### Generate allure report
You can use `allure-commandline` for that, see [Allure Docs](https://docs.qameta.io/allure/2.0/#_reporting) for details, or generate report with [allure-gradle](https://github.com/allure-framework/allure-gradle/) plugin.

### Compatibility with Android 8.0+ (API 26 or higher)
Look at https://github.com/allure-framework/allure-android/issues/12#issuecomment-486558848 for more information.

## Features

### Steps
Original [Steps](https://github.com/allure-framework/allure1/wiki/Steps) were improved:
* No more aspects required, `step` is an expression
* Name required, but not limited with const value as in annotations

You can use DSL-style steps anywhere in test method:
```kotlin
@Test
fun test() {
    step("First Step") {
        ...
    }
    step("Second Step") {
        ...
        myStep("Param") // Steps can be nested
    }
}

// Steps are expressions
fun myStep(param: String) = step("Do something with $param") {
    ...
}
```

### Soft Assertions
Soft Assertion can be used to continue test execution in case of minor assertion.
`SoftAssert` reports assertion inside `Step` in which it raised.

**SoftAssert marks test passed, result could be found only in report**

DSL-style:
```kotlin
fun myStep() = step("MyStep") {
    softly {
        checkThat("FirstAssert", true, IsEqual(false))
    }
}
```

### Basic screenshot
Screenshot uses UIautomator and appends it to step, in which it was executed

```kotlin
@Test
fun test() {
    deviceScreenshot("screenshot_name")
}
```

### Failshot rule
You can use `FailshotRule` to capture a screenshot in case of `Throwable` during test
```kotlin
class MyTest {

    @get:Rule
    val failshot = FailshotRule()

    // ...
}
```

### Logcat rule's (BETA)
You can use `LogcatDumpRule`, `LogcatClearRule` to clear and capture a device logs in case of `Throwable` during test
```kotlin
class MyTest {

    @get:Rule
    val ruleChain = RuleChain.outerRule(LogcatClearRule()).around(LogcatDumpRule())

    // ...
}
```

### Window hierarchy rule (BETA)
You can use `WindowHierarchyRule` to capture a window hierarchy via UIautomator in case of `Throwable` during test
```kotlin
class MyTest {

    @get:Rule
    val windowHierarchyRule = WindowHierarchyRule()

    // ...
}
```

### Allure annotations
You can use some allure annotation, like `@DisplayName`, `@Link`, `@Issue`, `@Owner`(BETA), `@SeverityLevel`(BETA) and others.

## Contributing to allure-android
Thanks to all people who contributed. Especially [Tinkoff](https://www.tinkoff.ru/) and [@Badya](https://github.com/badya) who started and maintaining allure-android.

## License
Allure Android is released under version 2.0 of the [Apache License][license]
