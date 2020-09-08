package br.com.emesistemas.topk.util

import androidx.test.platform.app.InstrumentationRegistry
import br.com.emesistemas.topk.BuildConfig

abstract class BaseUiTest {
    fun clearDatabase() {
        InstrumentationRegistry.getInstrumentation()
            .targetContext.deleteDatabase(BuildConfig.DATABASENAME)
    }

    fun setFlagIsTestingMockApiResponseOK(isRunning: Boolean) {
        BuildConfig.IS_TESTING_MOCK_API_RESPONSE_OK.set(isRunning)
        BuildConfig.IS_TESTING_MOCK_API_RESPONSE_ERROR.set(!isRunning)
    }

    fun setFlagIsTestingMockApiResponseERROR(isRunning: Boolean) {
        BuildConfig.IS_TESTING_MOCK_API_RESPONSE_OK.set(!isRunning)
        BuildConfig.IS_TESTING_MOCK_API_RESPONSE_ERROR.set(isRunning)
    }

}
