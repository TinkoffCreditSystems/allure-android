package io.qameta.allure.android.annotations

import io.qameta.allure.android.utils.CUSTOM_LINK_TYPE
import java.lang.annotation.Inherited

/**
 * Marker annotation. Annotations marked by this annotation will be discovered
 * by Allure and added to test results as a link.
 *
 * @see Link
 *
 * @see TmsLink
 *
 * @see Issue
 */
@MustBeDocumented
@Inherited
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS)
/**
 * Due do compatibility with Java 1.6, Kotlin doesn't support repeatable annotations.
 * This annotation is still required in here, because of generic parsing of annotation containers
 * @see [AnnotationUtils.extractRepeatable]
 */
@Repeatable
annotation class LinkAnnotation(
    /**
     * The value of link. In not specified will take value from `value()`
     * method of target annotation.
     *
     * @return the value of the link to add.
     */
    val value: String = DEFAULT_VALUE,
    /**
     * This type is used for create an icon for link. Also there is few reserved types such as issue and tms.
     *
     * @return the link type.
     */
    val type: String = CUSTOM_LINK_TYPE,
    /**
     * Url for link. By default will search for system property `allure.link.{type}.pattern`, and use it
     * to generate url.
     *
     * @return the link url.
     */
    val url: String = ""
) {
    companion object {
        const val DEFAULT_VALUE = "$$$$$$$\$__value__$$$$$$$$"
    }
}