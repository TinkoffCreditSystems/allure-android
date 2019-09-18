package ru.tinkoff.allure.annotations

import java.lang.annotation.Inherited

/**
 * This annotation used to specify owner for test case.
 */
@Inherited
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class TmsLink(val value: String)