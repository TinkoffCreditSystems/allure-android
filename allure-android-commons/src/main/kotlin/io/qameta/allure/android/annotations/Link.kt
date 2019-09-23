package io.qameta.allure.android.annotations


/**

 * Created by b.mukvich on 28.03.2017.

 * Use this annotation to add some links to results. Usage:
 * <pre>
 * &#064;Link("https://qameta.io")
 * public void myTest() {
 * ...
 * }
</pre> *
 */
@java.lang.annotation.Inherited
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Repeatable
annotation class Link(
        /**
         * Alias for [.name].

         * @return the link name.
         */
        val value: String = "",
        /**
         * Name for link, by default url.

         * @return the link name.
         */
        val name: String = "",
        /**
         * Url for link. By default will search for system property `allure.link.{type}.pattern`, and use it
         * to generate url.

         * @return the link url.
         */
        val url: String = "",
        /**
         * This type is used for create an icon for link. Also there is few reserved types such as issue and tms.

         * @return the link type.
         */
        val type: String = "custom")

