package io.qameta.allure.android.annotations

import java.lang.annotation.Inherited

/**
 * Wrapper annotation for [LinkAnnotation].
 */
@MustBeDocumented
@Inherited
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class LinkAnnotations(vararg val value: LinkAnnotation)