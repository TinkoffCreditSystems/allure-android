package io.qameta.allure.android

import org.junit.Test
import io.qameta.allure.android.model.Attachment
import io.qameta.allure.android.model.ExecutableItem
import io.qameta.allure.android.model.WithAttachments
import java.util.*
import kotlin.test.assertFalse

/**
 * Created by b.mukvich on 19.04.2017.
 */
class AllureStorageTest {
    private inner class TestClass : ExecutableItem()

    @Test
    fun `getObject byInterface`() {
        val clazzUUID = UUID.randomUUID().toString()
        val clazz = TestClass()
        AllureStorage.put(clazzUUID, clazz)
        val clazz2 = AllureStorage.get(clazzUUID, WithAttachments::class.java)
        clazz2.attachments.add(Attachment())
        val clazz3 = AllureStorage.get(clazzUUID, TestClass::class.java)
        assertFalse { clazz3.attachments.isEmpty() }
    }
}
