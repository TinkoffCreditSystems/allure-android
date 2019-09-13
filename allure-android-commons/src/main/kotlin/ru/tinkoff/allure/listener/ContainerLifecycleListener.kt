package ru.tinkoff.allure.listener

import ru.tinkoff.allure.model.TestResultContainer
import java.util.*

/**
 * @author Badya on 22.05.2017.
 */
interface ContainerLifecycleListener {
    fun beforeContainerStart(container: TestResultContainer) {
        // do nothing
    }

    fun afterContainerStart(container: TestResultContainer) {
        // do nothing
    }

    fun beforeContainerUpdate(container: TestResultContainer) {
        // do nothing
    }

    fun afterContainerUpdate(container: TestResultContainer) {
        // do nothing
    }

    fun beforeContainerStop(container: TestResultContainer) {
        // do nothing
    }

    fun afterContainerStop(container: TestResultContainer) {
        // do nothing
    }

    fun beforeContainerWrite(container: TestResultContainer) {
        // do nothing
    }

    fun afterContainerWrite(container: TestResultContainer) {
        // do nothing
    }
}

internal object ContainerLifecycleNotifier : ContainerLifecycleListener {
    private val containerListeners = ServiceLoader.load(ContainerLifecycleListener::class.java, this.javaClass.classLoader).toSet()

    override fun beforeContainerStart(container: TestResultContainer) {
        containerListeners.forEach { it.beforeContainerStart(container) }
    }

    override fun afterContainerStart(container: TestResultContainer) {
        containerListeners.forEach { it.afterContainerStart(container) }
    }

    override fun beforeContainerUpdate(container: TestResultContainer) {
        containerListeners.forEach { it.beforeContainerUpdate(container) }
    }

    override fun afterContainerUpdate(container: TestResultContainer) {
        containerListeners.forEach { it.afterContainerUpdate(container) }
    }

    override fun beforeContainerStop(container: TestResultContainer) {
        containerListeners.forEach { it.beforeContainerStop(container) }
    }

    override fun afterContainerStop(container: TestResultContainer) {
        containerListeners.forEach { it.afterContainerStop(container) }
    }

    override fun beforeContainerWrite(container: TestResultContainer) {
        containerListeners.forEach { it.beforeContainerUpdate(container) }
    }

    override fun afterContainerWrite(container: TestResultContainer) {
        containerListeners.forEach { it.afterContainerWrite(container) }
    }
}