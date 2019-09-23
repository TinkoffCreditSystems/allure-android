package io.qameta.allure.android.annotations

import java.lang.annotation.Inherited

/**
 * Created by b.mukvich on 28.03.2017.
 * Used to change display name for test in the report.
 */
@Inherited
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class DisplayName(val value: String)
