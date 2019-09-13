package ru.tinkoff.allure.annotations

import java.lang.annotation.Inherited

/**
 * Wrapper annotation for [Story].
 */
@Inherited
@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@kotlin.annotation.Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class Stories(vararg val value: Story)