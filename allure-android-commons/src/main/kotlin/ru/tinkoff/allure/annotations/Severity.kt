package ru.tinkoff.allure.annotations

import ru.tinkoff.allure.SeverityLevel
import java.lang.annotation.Inherited

/**
 * Used to set severity for tests.
 */
@Inherited
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class Severity(val value: SeverityLevel)