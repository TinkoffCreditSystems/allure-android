package io.qameta.allure.android.annotations

import java.lang.annotation.Inherited

/**
 * Wrapper annotation for [TmsLink].
 */
@Inherited
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER,
        AnnotationTarget.ANNOTATION_CLASS,
        AnnotationTarget.CLASS
)
annotation class TmsLinks(vararg val value: TmsLink)