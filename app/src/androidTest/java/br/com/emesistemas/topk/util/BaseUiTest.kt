package br.com.emesistemas.topk.util

import androidx.test.platform.app.InstrumentationRegistry
import br.com.emesistemas.topk.BuildConfig

abstract class BaseUiTest {
    fun clearDatabase() {
        InstrumentationRegistry.getInstrumentation()
            .targetContext.deleteDatabase(BuildConfig.DATABASENAME)
    }

    fun setFlagIsUiTestingRunning(isRunning: Boolean) {
        BuildConfig.IS_UI_TESTING.set(isRunning)
    }
}
