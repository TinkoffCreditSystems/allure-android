package io.qameta.allure.android

enum class SeverityLevel
constructor(private val value: String) {
    BLOCKER("blocker"),
    CRITICAL("critical"),
    NORMAL("normal"),
    MINOR("minor"),
    TRIVIAL("trivial");

    fun value(): String {
        return value
    }

    override fun toString(): String {
        return value()
    }
}