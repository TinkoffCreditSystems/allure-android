package io.qameta.allure.android.annotations

import java.lang.annotation.Inherited

/**
 * Used by Allure Enterprise to link test cases with related test methods.
 */
@Inherited
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class AllureId(val value: String)