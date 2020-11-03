package io.qameta.allure.android.annotations

import io.qameta.allure.android.utils.SEVERITY_LABEL_NAME
import io.qameta.allure.android.utils.TAG_LABEL_NAME
import java.lang.annotation.Inherited

/**
 * Used to set tag for tests.
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
@LabelAnnotation(name = TAG_LABEL_NAME)
annotation class Tag(val value: String)
