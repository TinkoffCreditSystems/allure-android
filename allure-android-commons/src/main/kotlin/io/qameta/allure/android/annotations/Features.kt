package io.qameta.allure.android.annotations

import java.lang.annotation.Inherited

/**
 * Wrapper annotation for [Feature].
 */
@Inherited
@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@kotlin.annotation.Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class Features(vararg val value: Feature)