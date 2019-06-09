package ru.tinkoff.allure.android

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.internal.util.AndroidRunnerParams
import org.junit.runner.notification.RunNotifier
import org.junit.runners.model.InitializationError

/**
 * @author b.mukvich on 05.06.2017.
 */
class AllureRunner
/**
 * Constructs a new instance of the default runner
 */
@Throws(InitializationError::class)
constructor(klass: Class<*>, runnerParams: AndroidRunnerParams) : AndroidJUnit4ClassRunner(klass, runnerParams) {
    override fun run(notifier: RunNotifier?) {

        super.run(notifier)
    }
}