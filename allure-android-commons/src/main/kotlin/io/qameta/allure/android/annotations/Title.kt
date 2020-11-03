package io.qameta.allure.android.annotations

/**
 * Created by b.mukvich on 23.03.2017.
 */

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER,
        AnnotationTarget.CLASS,
        AnnotationTarget.FILE
)
annotation class Title(val value: String)