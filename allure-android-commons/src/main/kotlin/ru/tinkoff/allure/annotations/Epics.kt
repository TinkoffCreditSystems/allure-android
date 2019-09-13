package ru.tinkoff.allure.annotations

import java.lang.annotation.Inherited

/**
 * Wrapper annotation for {@link Epic}.
 */
@Inherited
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class Epics(vararg val value: Epic)