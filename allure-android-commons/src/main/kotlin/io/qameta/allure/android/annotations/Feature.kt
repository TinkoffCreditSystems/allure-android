package io.qameta.allure.android.annotations

import io.qameta.allure.android.utils.FEATURE_LABEL_NAME
import java.lang.annotation.Inherited

/**
 * Used to mark tests with feature label.
 */
@Inherited
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER,
        AnnotationTarget.CLASS,
        AnnotationTarget.FILE
)
@Repeatable
@LabelAnnotation(name = FEATURE_LABEL_NAME)
annotation class Feature(val value: String)