package io.qameta.allure.android.annotations

/**
 * @author b.mukvich on 31.05.2017.
 */
@java.lang.annotation.Inherited
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER,
        AnnotationTarget.CLASS,
        AnnotationTarget.FILE
)
annotation class Links(vararg val value: Link)