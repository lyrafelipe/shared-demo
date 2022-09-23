package com.lyrafelipe.sharedtestcode.rules

import io.mockk.mockkClass
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.test.mock.MockProvider

class MockProviderRule : TestWatcher() {

    override fun starting(description: Description) {
        super.starting(description)
        MockProvider.register { mockkClass(it) }
    }
}