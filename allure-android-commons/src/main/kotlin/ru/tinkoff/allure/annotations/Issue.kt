package ru.tinkoff.allure.annotations

import java.lang.annotation.Inherited


/**
 * Created by b.mukvich on 23.03.2017.
 * Used to link tests with issues.
 */
@Inherited
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class Issue(val value: String)