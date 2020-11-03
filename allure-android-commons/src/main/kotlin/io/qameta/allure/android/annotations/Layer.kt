package io.qameta.allure.android.annotations

import io.qameta.allure.android.utils.LAYER_LABEL_NAME
import java.lang.annotation.Inherited

/**
 * Used to set layer for tests.
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
@LabelAnnotation(name = LAYER_LABEL_NAME)
annotation class Layer(val value: String)
