package ru.tinkoff.allure.annotations

import java.lang.annotation.Inherited
import kotlin.annotation.Repeatable

/**
 * Used to mark tests with epic label.
 */
@Inherited
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Repeatable
annotation class Epic(val value: String)