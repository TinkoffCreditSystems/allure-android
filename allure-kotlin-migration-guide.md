# Allure Kotlin Migration Guide

## Why allure-kotlin?

Why not `allure-android`? The Allure Android adapter implementation is limited and it doesn't support all of the features of Allure. Its drawbacks are following:
* no support for all annotations e.g. `@Description`, `@LinkAnnotations`
* limited API when comparing to `allure-java`
* poor test coverage
* tight coupling with Android Instrumentation tests, so it can't be easily reused for unit tests or Robolectric tests

Due to the mentioned limitations `allure-kotlin` was born as a core for different Kotlin and Android test frameworks.

## Migration guide
### Step 1 - `allure-android-model` migration
This is a module used under the hood and shouldn't be accessed by consumers code. 
If you are using those classes, beware that some of them may have different nullability and mutability.  

1. Replace dependency
   ```gradle
   dependencies {
       //REMOVE allure-android dependency
       androidTestImplementation "io.qameta.allure:allure-android-model:$ALLURE_ANDROID_VERSION"
  
       //ADD allure-kotlin dependency
       androidTestImplementation "io.qameta.allure:allure-kotlin-model:$ALLURE_KOTLIN_VERSION"
   }
    ```
1. Change all imports from `io.qameta.allure.android.model.*` -> `io.qameta.allure.kotlin.model.*`

### Step 2 - `allure-android-commons` migration
1. Replace dependencies
   ```gradle
   dependencies {
       //REMOVE allure-android dependency
       androidTestImplementation "io.qameta.allure:allure-android-commons:$ALLURE_ANDROID_VERSION"
  
       //ADD allure-kotlin dependencies
       androidTestImplementation "io.qameta.allure:allure-kotlin-commons:$ALLURE_KOTLIN_VERSION"
       androidTestImplementation "io.qameta.allure:allure-kotlin-junit4:$ALLURE_KOTLIN_VERSION"
   }
    ```
1. Migrate core classes
    1. Migrate global `step` function from `io.qameta.allure.android.step` to `io.qameta.allure.kotlin.Allure#step` using the following example (the API is a bit different and allows for specifying parameters inside the lambda):
        ```kotlin
        //allure-android definition
        step("Step1", Parameter("param1", "true"), Parameter("param2", "false")) {
            assertTrue(true)
        }
        ...
        //allure-kotlin definition
        Allure.step("Step 1") {
            parameter("param1", "true")
            parameter("param2", "false")
            assertTrue(true)
        }
        ```
        Explore `Allure` API as it allows to define labels, epics and other allure components from the code, instead of only via annotations.
    1. Migrate `AllureRunListener` from `io.qameta.allure.android.AllureRunListener` -> `io.qameta.allure.kotlin.junit4.AllureJunit4`
    1. Migrate `AllureLifecycle` from `io.qameta.allure.android.AllureLifecycle` -> `io.qameta.allure.kotlin.AllureLifecycle` (which has a lot more capabilities and allows to attach lifecycle listeners)
    1. Change import for `AllureRunner` from `io.qameta.allure.android.AllureRunner` -> `io.qameta.allure.kotlin.junit4.AllureRunner`
1. Migrate package `annotations`
    1. Change all imports from `io.qameta.allure.android.annotations.*` -> `io.qameta.allure.kotlin.*` (explore new annotations package as support for many more annotations has been added)
    1. Change imports for Allure Junit4 annotations `DisplayName`, `Tag`, `Tags` from `io.qameta.allure.android.annotations.*` -> `io.qameta.allure.kotlin.junit4.*`
    1. Change import for `SeverityLevel` from `io.qameta.allure.android.SeverityLevel` -> `io.qameta.allure.kotlin.SeverityLevel`
1. Migrate package `io`
    1. Change all imports from `io.qameta.allure.android.io.*` -> `io.qameta.allure.kotlin.*`
    1. In case you have custom `AllureResultsWriter` adjust it as the API has changed a bit
    1. `AllureResultsReader` & `FileSystemResultsReader` have been removed as they are not used anymore
    1. In case you are using any of the constants like from `AllureFileConstants` (like `TEXT_PLAIN`, `TXT_EXTENSION`) define them yourself as they are not part of public API anymore.
1. Migrate package `listener` - change all imports from `io.qameta.allure.android.listener.*` -> `io.qameta.allure.kotlin.listener.*`
1. Migrate package `serialization` - it was used internally and is no longer needed.
1. Migrate package `utils` - it was used to parse the annotations to allure results. It shouldn't be accesed by consumer code. If you were using any of those methods take a look at ` io.qameta.allure.kotlin.util.ResultsUtils` for replacement (as the implementation of parsing annotations has changed).
 

### Step 3 - `allure-espresso` migration
1. Replace dependency
   ```gradle
   dependencies {
       //REMOVE allure-android dependency
       androidTestImplementation "io.qameta.allure:allure-espresso:$ALLURE_ANDROID_VERSION"
  
       //ADD allure-kotlin dependency
       androidTestImplementation "io.qameta.allure:allure-kotlin-android:$ALLURE_KOTLIN_VERSION"
   }
1. Migrate runners (don't forget migrating `testInstrumentationRunner` gradle configuration)
    1. Change import for `AllureAndroidRunner` from `io.qameta.allure.espresso.AllureAndroidRunner` -> `io.qameta.allure.android.runners.AllureAndroidJUnitRunner`
    1. Change import for `MultiDexAllureAndroidRunner` from `io.qameta.allure.espresso.MultiDexAllureAndroidRunner` -> `io.qameta.allure.android.runners.MultiDexAllureAndroidJUnitRunner` 
1. Replace `AllureAndroidListener` usages with `io.qameta.allure.kotlin.junit4.AllureJunit4`. In case you want to have external storage permissions automatically granted as well (default behaviour of `AllureAndroidListener`) attach addititional test listener `io.qameta.allure.android.listeners.ExternalStoragePermissionsListener`
1. Migrate test rules
    1. Replace `FailshotRule` with `io.qameta.allure.android.rules.ScreenshotRule` that includes configuration to take a screenshot after sucessfull tests.
    1. Replace `LogcatClearRule` & `LogcatDumpRule` with `io.qameta.allure.android.rules.LogcatRule` that merges behaviour of those 2 rules.
    1. Change import for `WindowHierarchyRule` to `io.qameta.allure.android.rules.WindowHierarchyRule`
1. Change import for `AllureAndroidLifecycle` from `io.qameta.allure.espresso.io.qameta.allure.espresso` -> `io.qameta.allure.android.AllureAndroidLifecycle`
    1. Migrate `addAttachment` / `prepareAttachment` methods as the signature changed (renamed & reordered parameters, fixed nullability). Most importantly the `file` parameter type was changed from `File` to either `ByteArray` or `InputStream`. The easiest way to migrate it is to convert the `file` to `InputStream` (e.g. `FileInputStream(file)`).
    1. Where possible migrate `addAttachment` to `Allure.attachment` as this is the API that should be used for updating allure results.
1. Change import for `LogCatListener` from `io.qameta.allure.espresso.listener.LogCatListener` -> `io.qameta.allure.android.listeners.LogcatListener`
1. Migrate screenshots taking from `deviceScreenshot` to `io.qameta.allure.android.allureScreenshot` that allows to specify quality and scale of a screenshot.
