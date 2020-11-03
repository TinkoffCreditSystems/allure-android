package io.qameta.allure.android.annotations


/**
 * Created by b.mukvich on 23.03.2017.
 */

@Retention(AnnotationRetention.RUNTIME)
@Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER,
        AnnotationTarget.CLASS,
        AnnotationTarget.FILE
)
annotation class Description(
        /**
         * Simple description text as String.

         * @return Description text.
         */
        val value: String = "",
        /**
         * Use annotated method's javadoc to extract description that
         * supports html markdown.

         * @return boolean flag to enable description extraction from javadoc.
         */
        val useJavaDoc: Boolean = false)