package io.qameta.allure.android.annotations

import io.qameta.allure.android.utils.ALLURE_ID_LABEL_NAME
import java.lang.annotation.Inherited

/**
 * Used by Allure Enterprise to link test cases with related test methods.
 */
@MustBeDocumented
@Inherited
@Retention(AnnotationRetention.RUNTIME)
@Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER
)
@LabelAnnotation(name = ALLURE_ID_LABEL_NAME)
annotation class AllureId(val value: String)