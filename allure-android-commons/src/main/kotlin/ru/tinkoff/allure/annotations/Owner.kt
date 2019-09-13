package ru.tinkoff.allure.annotations

import java.lang.annotation.Inherited

/**
 * This annotation used to specify owner for test case.
 */
@Inherited
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class Owner(val value: String)