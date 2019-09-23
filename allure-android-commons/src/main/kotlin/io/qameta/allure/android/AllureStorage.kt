package io.qameta.allure.android

import io.qameta.allure.android.model.StepResult
import io.qameta.allure.android.model.TestResult
import io.qameta.allure.android.model.TestResultContainer
import io.qameta.allure.android.model.WithSteps
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * @author Badya on 18.04.2017.
 */
internal object AllureStorage {
    private val storage: MutableMap<String, Any> = ConcurrentHashMap()

    private val currentStepContext: ThreadLocal<LinkedList<String?>> = object : InheritableThreadLocal<LinkedList<String?>>() {
        public override fun initialValue() = LinkedList<String?>()
    }

    fun getCurrentStep(): String {
        return requireNotNull(currentStepContext.get().firstOrNull()) { "Current Step doesn't Exist" }
    }

    fun getTest(): String {
        return requireNotNull(currentStepContext.get().lastOrNull()) { "Root Step doesn't Exist" }
    }

    fun startTest(uuid: String) {
        currentStepContext.get().push(uuid)
    }

    fun startStep(uuid: String) {
        currentStepContext.get().push(uuid)
    }

    fun stopStep() {
        currentStepContext.get().pop()
    }

    fun clearStepContext() {
        currentStepContext.remove()
    }

    fun getStep(uuid: String?): StepResult {
        return get(uuid, StepResult::class.java)
    }

    fun addStep(parentUUID: String?, stepResult: StepResult) {
        put(stepResult.uuid, stepResult)
        get(parentUUID, WithSteps::class.java).steps.add(stepResult)
    }

    fun removeStep(uuid: String?): StepResult? {
        return remove(uuid, StepResult::class.java)
    }

    fun getContainer(uuid: String?): TestResultContainer? {
        return get(uuid, TestResultContainer::class.java)
    }

    fun addContainer(container: TestResultContainer) {
        put(container.uuid, container)
    }

    fun removeContainer(uuid: String?): TestResultContainer? {
        return remove(uuid, TestResultContainer::class.java)
    }

    fun getTestResult(uuid: String?): TestResult? {
        return get(uuid, TestResult::class.java)
    }

    fun addTestResult(testResult: TestResult) {
        put(testResult.uuid, testResult)
    }

    fun removeTestResult(uuid: String?): TestResult? {
        return remove(uuid, TestResult::class.java)
    }

    internal fun <T : Any> put(uuid: String?, item: T): T {
        val key = requireNotNull(uuid, { "Failed to put item to storage: uuid can't be null" })
        storage[key] = item
        return item
    }

    internal fun <T> get(uuid: String?, type: Class<out T>): T {
        val key = requireNotNull(uuid) { "Failed to get item from storage: uuid can't be null" }
        return type.cast(storage[key])
    }

    internal fun <T> remove(uuid: String?, type: Class<out T>): T {
        val key = requireNotNull(uuid, { "Failed to remove item from storage: uuid can't be null" })
        return type.cast(storage.remove(key))
    }


}
