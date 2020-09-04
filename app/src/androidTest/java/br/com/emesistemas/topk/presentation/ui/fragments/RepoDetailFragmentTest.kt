package br.com.emesistemas.topk.presentation.ui.fragments

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import br.com.emesistemas.topk.BuildConfig
import br.com.emesistemas.topk.R
import br.com.emesistemas.topk.presentation.ui.activities.MainActivity
import br.com.emesistemas.topk.presentation.ui.adapters.RepoListAdapter
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.startsWith
import org.junit.After
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class RepoDetailFragmentTest {

    //@get:Rule
    //val activityRule = ActivityScenarioRule(MainActivity::class.java)

    lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        BuildConfig.IS_UI_TESTING.set(true)
        val intent = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
        scenario = launchActivity(intent)
    }

    @After
    fun tearDown() {
        if(::scenario.isInitialized){
            scenario.close()
        }
        BuildConfig.IS_UI_TESTING.set(false)
        InstrumentationRegistry.getInstrumentation()
            .targetContext.deleteDatabase(BuildConfig.DATABASENAME_TEST)
    }

    @Test
    fun test_A_isAllRepoDetailVisible() {

        onView(withId(R.id.rvRepoList))
            .perform(
                actionOnItemAtPosition<RepoListAdapter.RepoViewHolder>(0, click())
            )

        onView(withId(R.id.ivBackAvatar))
            .check(matches(isDisplayed()))

        onView(allOf(withId(R.id.tvAuthor)
            , withText("google")
            , isDisplayed()))

        onView(allOf(withId(R.id.tvRepo)
            , withText("iosched")
            , isDisplayed()))

        onView(allOf(withId(R.id.cardContainer)
            , isDisplayed()))

        onView(allOf(withId(R.id.ivStar)
            , isDisplayed()))

        onView(allOf(withId(R.id.ivFork)
            , isDisplayed()))

        onView(allOf(withId(R.id.ivIssue)
            , isDisplayed()))

        onView(allOf(withId(R.id.tvStarsCount)
            , withText("20098")
            , isDisplayed()))

        onView(allOf(withId(R.id.tvForksCount)
            , withText("6098")
            , isDisplayed()))

        onView(allOf(withId(R.id.tvIssuesCount)
            , withText("50")
            , isDisplayed()))

        onView(allOf(withId(R.id.tvCreated)
            , withText(startsWith("Criado em "))
            , isDisplayed()))

        onView(allOf(withId(R.id.tvUpdated)
            , withText(startsWith("Atualizado em "))
            , isDisplayed()))

        onView(allOf(withId(R.id.btOpenWeb)
            , withText("VER NA WEB")
            , isDisplayed()))
    }

    @Test
    fun test_B_Toolbar_hasHomeAsUpButton() {
        onView(
            allOf(
                withId(R.id.toolbar)
                , withId(android.R.id.home)
                , isDisplayed()
            )
        )
    }

    @Test
    fun test_C_ToolbarTitle_isEmpty() {
        onView(
            allOf(
                withId(R.id.toolbar)
                , withText("")
            )
        )
    }
}