package io.qameta.allure.android.annotations

import io.qameta.allure.android.utils.SEVERITY_LABEL_NAME
import io.qameta.allure.android.utils.STORY_LABEL_NAME
import java.lang.annotation.Inherited

/**
 * Used to mark test case with a story label.
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
@LabelAnnotation(name = STORY_LABEL_NAME)
annotation class Story(val value: String)