package io.qameta.allure.android.annotations

import io.qameta.allure.android.utils.EPIC_LABEL_NAME
import java.lang.annotation.Inherited
import kotlin.annotation.Repeatable

/**
 * Used to mark tests with epic label.
 */
@Inherited
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER,
        AnnotationTarget.ANNOTATION_CLASS,
        AnnotationTarget.CLASS
)
@Repeatable
@LabelAnnotation(name = EPIC_LABEL_NAME)
annotation class Epic(val value: String)