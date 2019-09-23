package io.qameta.allure.android.listener

import io.qameta.allure.android.model.StepResult
import java.util.*

/**
 * @author Badya on 22.05.2017.
 */

interface StepLifecycleListener {
    fun beforeStepStart(result: StepResult) {
        // do nothing
    }

    fun afterStepStart(result: StepResult) {
        // do nothing
    }

    fun beforeStepUpdate(result: StepResult) {
        // do nothing
    }

    fun afterStepUpdate(result: StepResult) {
        // do nothing
    }

    fun beforeStepStop(result: StepResult) {
        // do nothing
    }

    fun afterStepStop(result: StepResult) {
        // do nothing
    }
}

internal object StepLifecycleNotifier : StepLifecycleListener {
    private val stepListeners = ServiceLoader.load(StepLifecycleListener::class.java, this.javaClass.classLoader).toSet()

    override fun beforeStepStart(result: StepResult) {
        stepListeners.forEach { it.beforeStepStart(result) }
    }

    override fun afterStepStart(result: StepResult) {
        stepListeners.forEach { it.afterStepStart(result) }
    }

    override fun beforeStepUpdate(result: StepResult) {
        stepListeners.forEach { it.beforeStepUpdate(result) }
    }

    override fun afterStepUpdate(result: StepResult) {
        stepListeners.forEach { it.afterStepUpdate(result) }
    }

    override fun beforeStepStop(result: StepResult) {
        stepListeners.forEach { it.beforeStepStop(result) }
    }

    override fun afterStepStop(result: StepResult) {
        stepListeners.forEach { it.afterStepStop(result) }
    }
}