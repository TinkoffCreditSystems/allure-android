package io.qameta.allure.android.annotations

import java.lang.annotation.Inherited

/**
 * Used to mark test case with a story label.
 */
@Inherited
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Repeatable
annotation class Story(val value: String)