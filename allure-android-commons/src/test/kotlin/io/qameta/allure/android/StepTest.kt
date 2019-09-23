package io.qameta.allure.android

import org.junit.Test
import org.junit.runner.RunWith
import io.qameta.allure.android.model.Parameter
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * @author b.mukvich on 27.06.2017.
 */
@RunWith(AllureRunner::class)
class StepTest {
    @Test
    fun `params inside step`() {
        val login = "login"
        val pass = "password"
        val stepInvocation = testStep(login, pass)

        AllureCommonLifecycle.updateTestCase {
            assertTrue {
                (this.steps[0].parameters.size == 2) and
                        (this.steps[0].parameters.contains(Parameter(name = "login", value = login))) and
                        (this.steps[0].parameters.contains(Parameter(name = "password", value = pass)))
            }
        }

        assertEquals("$login $pass", stepInvocation)
    }

    private fun testStep(login: String, pass: String) = step(
            "loginStep",
            Parameter(name = "login", value = login),
            Parameter(name = "password", value = pass)) {
        doLogin(login, pass)
    }

    private fun doLogin(login: String, pass: String) = "$login $pass"
}
