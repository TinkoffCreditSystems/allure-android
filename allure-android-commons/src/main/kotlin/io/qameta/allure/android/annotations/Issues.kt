package io.qameta.allure.android.annotations

import java.lang.annotation.Inherited

/**
 * Wrapper annotation for [Issue].
 */
@Inherited
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER,
        AnnotationTarget.CLASS,
        AnnotationTarget.FILE
)
annotation class Issues(vararg val value: Issue)
