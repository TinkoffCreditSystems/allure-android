package io.qameta.allure.android.annotations

import io.qameta.allure.android.utils.OWNER_LABEL_NAME
import java.lang.annotation.Inherited

/**
 * This annotation used to specify owner for test case.
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
@LabelAnnotation(name = OWNER_LABEL_NAME)
annotation class Owner(val value: String)