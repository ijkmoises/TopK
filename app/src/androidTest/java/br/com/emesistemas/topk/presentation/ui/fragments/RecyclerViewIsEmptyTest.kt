package br.com.emesistemas.topk.presentation.ui.fragments

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.emesistemas.topk.R
import br.com.emesistemas.topk.presentation.ui.activities.MainActivity
import br.com.emesistemas.topk.util.BaseUiTest
import br.com.emesistemas.topk.util.EspressoIdlingResourceRule
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class RecyclerViewIsEmptyTest : BaseUiTest() {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    @Before
    fun setup() {
        clearDatabase()
        setFlagIsTestingMockApiResponseERROR(true)
    }

    @Test
    fun whenApiReturnSomeError_and_cacheIsEmpty_ShowViewThatInformUnavailableData() {
        onView(withId(R.id.container_adapter_empty))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btOpenWeb))
            .check(matches(isDisplayed()))
    }
}