package io.qameta.allure.android

import org.junit.runner.notification.RunNotifier
import org.junit.runners.BlockJUnit4ClassRunner

/**
 * @author Badya on 01.06.2017.
 */
open class AllureRunner(klass: Class<*>) : BlockJUnit4ClassRunner(klass) {
    override fun run(notifier: RunNotifier) {
        val listener = AllureRunListener()
        notifier.addListener(listener)
        listener.testRunStarted()
        try {
            super.run(notifier)
        } finally {
            listener.testRunFinished()
            notifier.removeListener(listener)
        }
    }
}