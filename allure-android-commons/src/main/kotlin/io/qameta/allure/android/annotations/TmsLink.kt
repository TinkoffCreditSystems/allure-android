package io.qameta.allure.android.annotations

import io.qameta.allure.android.utils.TMS_LINK_TYPE
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
        AnnotationTarget.ANNOTATION_CLASS,
        AnnotationTarget.CLASS
)
@LinkAnnotation(type = TMS_LINK_TYPE)
annotation class TmsLink(val value: String)