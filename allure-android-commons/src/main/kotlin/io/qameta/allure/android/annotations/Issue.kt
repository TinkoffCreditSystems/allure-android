package io.qameta.allure.android.annotations

import io.qameta.allure.android.utils.ISSUE_LINK_TYPE
import java.lang.annotation.Inherited


/**
 * Created by b.mukvich on 23.03.2017.
 * Used to link tests with issues.
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
@LinkAnnotation(type = ISSUE_LINK_TYPE)
annotation class Issue(val value: String)