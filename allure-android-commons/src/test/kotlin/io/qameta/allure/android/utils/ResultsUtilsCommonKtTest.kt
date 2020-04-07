package io.qameta.allure.android.utils

import org.junit.Test

import org.junit.Assert.*

class ResultsUtilsCommonKtTest {

    @Test
    fun `createIssueLink should return valid url`() {
        val id = "ISSUE-11"
        val pattern = "https://www.issue.tracker.com/{}"
        System.setProperty("allure.link.issue.pattern", pattern)

        val link = createIssueLink(id)
        val expectedUrl = pattern.replace("{}", id)

        assertEquals(expectedUrl, link.url)
    }

    @Test
    fun `createTmsLink should return valid url`() {
        val id = "TMS-11"
        val pattern = "https://www.issue.tracker.com/{}"
        System.setProperty("allure.link.tms.pattern", pattern)

        val link = createTmsLink(id)
        val expectedUrl = pattern.replace("{}", id)

        assertEquals(expectedUrl, link.url)
    }

}