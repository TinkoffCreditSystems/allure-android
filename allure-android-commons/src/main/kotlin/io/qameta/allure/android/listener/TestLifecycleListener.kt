package io.qameta.allure.android.listener

import io.qameta.allure.android.model.TestResult
import java.util.*

/**
 * @author Badya on 22.05.2017.
 */
interface TestLifecycleListener {
    fun beforeTestSchedule(result: TestResult) {
        //do nothing
    }

    fun afterTestSchedule(result: TestResult) {
        //do nothing
    }

    fun beforeTestStart(result: TestResult) {
        //do nothing
    }

    fun afterTestStart(result: TestResult) {
        //do nothing
    }

    fun beforeTestUpdate(result: TestResult) {
        //do nothing
    }

    fun afterTestUpdate(result: TestResult) {
        //do nothing
    }

    fun beforeTestStop(result: TestResult) {
        //do nothing
    }

    fun afterTestStop(result: TestResult) {
        //do nothing
    }

    fun beforeTestWrite(result: TestResult) {
        //do nothing
    }

    fun afterTestWrite(result: TestResult) {
        //do nothing
    }

}

internal object TestLifecycleNotifier : TestLifecycleListener {
    private val testListeners = ServiceLoader.load(TestLifecycleListener::class.java, this.javaClass.classLoader).toSet()

    override fun beforeTestSchedule(result: TestResult) {
        testListeners.forEach { it.beforeTestSchedule(result) }
    }

    override fun afterTestSchedule(result: TestResult) {
        testListeners.forEach { it.afterTestSchedule(result) }
    }

    override fun beforeTestStart(result: TestResult) {
        testListeners.forEach { it.beforeTestStart(result) }
    }

    override fun afterTestStart(result: TestResult) {
        testListeners.forEach { it.afterTestStart(result) }
    }

    override fun beforeTestUpdate(result: TestResult) {
        testListeners.forEach { it.beforeTestUpdate(result) }
    }

    override fun afterTestUpdate(result: TestResult) {
        testListeners.forEach { it.afterTestUpdate(result) }
    }

    override fun beforeTestStop(result: TestResult) {
        testListeners.forEach { it.beforeTestStop(result) }
    }

    override fun afterTestStop(result: TestResult) {
        testListeners.forEach { it.afterTestStop(result) }
    }

    override fun beforeTestWrite(result: TestResult) {
        testListeners.forEach { it.beforeTestWrite(result) }
    }

    override fun afterTestWrite(result: TestResult) {
        testListeners.forEach { it.afterTestWrite(result) }
    }

}