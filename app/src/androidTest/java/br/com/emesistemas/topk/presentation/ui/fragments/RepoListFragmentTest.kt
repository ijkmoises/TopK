package br.com.emesistemas.topk.presentation.ui.fragments


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import br.com.emesistemas.topk.R
import br.com.emesistemas.topk.model.Item
import br.com.emesistemas.topk.model.Owner
import br.com.emesistemas.topk.presentation.ui.activities.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RepoListFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    private val items: List<Item> = listOf(
        Item(id = 100, page = 1, owner = Owner(login = "John")),
        Item(id = 200, page = 1, owner = Owner(login = "Mark")),
        Item(id = 300, page = 1, owner = Owner(login = "Vany")),
        Item(id = 400, page = 2, owner = Owner(login = "Ness")),
        Item(id = 500, page = 2, owner = Owner(login = "Ghose")),
        Item(id = 600, page = 2, owner = Owner(login = "Bill"))
    )


    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {
        InstrumentationRegistry.getInstrumentation().targetContext.deleteDatabase("topk_test.db")
    }

    @Test
    fun a_test_isListFragmentVisible_onAppLaunch() {

        //val intent = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
        //scenario = launchActivity(intent)

        onView(withId(R.id.rvRepoList))
            .check(matches(isDisplayed()))

        Thread.sleep(2000)

        //Clica no item
//        onView(withId(R.id.rvRepoList))
//            .perform(RecyclerViewActions
//                .actionOnItemAtPosition<RepoListAdapter.RepoViewHolder>(0, ))
    }
}