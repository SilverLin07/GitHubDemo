package com.example.githubdemo

import android.content.Context
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import com.example.common.util.DataStoreUtils
import com.example.common.util.eventbus.EventBus
import com.example.githubdemo.data.bean.RepositoryItem
import com.example.githubdemo.data.bean.UserInfoBean
import com.example.githubdemo.data.network.MainService
import com.example.githubdemo.viewmodel.MineSettingViewModel
import com.example.githubdemo.viewmodel.MineViewModel
import com.example.githubdemo.viewmodel.SearchViewModel
import com.example.githubdemo.viewmodel.StarredViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
class MyComposeTest {
  private val context = ApplicationProvider.getApplicationContext<Context>()

  @get:Rule(order = 0)
  val hiltRule = HiltAndroidRule(this)

  @get:Rule(order = 1)
  val composeTestRule = createAndroidComposeRule<TestMainActivity>()

  private lateinit var searchViewModel: SearchViewModel
  private lateinit var mineViewModel: MineViewModel
  private lateinit var mineSettingViewModel: MineSettingViewModel
  private lateinit var starredViewModel: StarredViewModel

  @Inject
  lateinit var service: MainService

  @Inject
  lateinit var eventBus: EventBus

  // use your own valid accessToken to mock the signed in state
  private val ownValidAccessToken: String = ""

  @Before
  fun setup() {
    hiltRule.inject()
    IdlingRegistry.getInstance().register(CountingIdlingResourceSingleton.countingIdlingResource)
  }

  @After
  fun clear() {
    IdlingRegistry.getInstance().unregister(CountingIdlingResourceSingleton.countingIdlingResource)
  }

  @Test
  fun testInitUI() {
    composeTestRule.onNodeWithText(context.getString(R.string.explore)).assertIsDisplayed()
    composeTestRule.onNodeWithText(context.getString(R.string.search)).assertIsDisplayed()
    composeTestRule.onNodeWithText(context.getString(R.string.mine)).assertIsDisplayed()
  }

  @Test
  fun testSearchUI() {
    // click search button and navigate to search page
    composeTestRule.onNodeWithText(context.getString(R.string.search)).performClick()

    composeTestRule.onNodeWithTag("SearchInputArea").assertIsDisplayed()
    composeTestRule.onNodeWithText("Languages").assertIsDisplayed()
    composeTestRule.onNodeWithText("Sort by").assertIsDisplayed()

    // click Languages by filter and show Languages by filters
    composeTestRule.onNodeWithText("Languages").performClick()
    val sortLanguages =
      listOf("Java", "Kotlin", "C", "C++", "JavaScript", "HTML", "Dart", "C#", "Python")
    for (item in sortLanguages) {
      composeTestRule.onNodeWithText(item).assertIsDisplayed()
    }

    // click Sort by filter and show Sort by filters
    composeTestRule.onNodeWithText("Sort by").performClick()
    val sortBy = listOf("Best match", "Most stars", "Most forks", "Recently updated")
    for (item in sortBy) {
      composeTestRule.onNodeWithText(item).assertIsDisplayed()
    }
  }

  @Test
  fun testSearchRstUI() {
    // click search button and navigate to search page
    composeTestRule.onNodeWithText(context.getString(R.string.search)).performClick()

    // input the key word to search
    composeTestRule.onNodeWithTag("SearchInputArea").performTextInput("Android")
    composeTestRule.onNodeWithText("Android").assertIsDisplayed()

    searchViewModel = SearchViewModel(service, eventBus)
    // use the search api get the result and compare with the search result in the ui
    var list: List<RepositoryItem>? = null
    searchViewModel.search("Android", callBack = { rst ->
      list = rst
    }, finally = {
      CountingIdlingResourceSingleton.decrement()
    })
    CountingIdlingResourceSingleton.increment()
    composeTestRule.waitForIdle()

    if (!list.isNullOrEmpty()) {
      val firstItem = list!![0]
      composeTestRule.onNodeWithText(firstItem.fullName).assertIsDisplayed()

      val lastItem = list!!.last()
      composeTestRule.onNodeWithTag("searchLazyColumn", useUnmergedTree = true)
        .performScrollToIndex(list!!.size - 1)
      composeTestRule.onNodeWithText(lastItem.fullName).assertIsDisplayed()
    }
  }

  @Test
  fun testMineUI() {
    // click mine button and navigate to mine page
    composeTestRule.onNodeWithText(context.getString(R.string.mine)).performClick()

    val accessToken = DataStoreUtils.readStringData(DataStoreUtils.ACCESS_TOKEN, "")

    // if th accessToken is blank then ui should show the sign in button
    if (accessToken.isBlank()) {
      composeTestRule.onNodeWithText("Sign in to GitHub").assertIsDisplayed()
      composeTestRule.onNodeWithText("Sign in with accessToken").assertIsDisplayed()
    }
  }

  @Test
  fun testSignedInMineUI() {
    // use your own valid accessToken to mock the signed in state
    DataStoreUtils.saveSyncStringData(
      DataStoreUtils.ACCESS_TOKEN,
      ownValidAccessToken
    )

    // click mine button and navigate to mine page
    composeTestRule.onNodeWithText(context.getString(R.string.mine)).performClick()
    composeTestRule.onNodeWithContentDescription("optIcon").assertIsDisplayed()

    // use the myRepositories api get the result and compare with the myRepositories result in the ui
    mineViewModel = MineViewModel(service, eventBus)
    var list: List<RepositoryItem>? = null
    mineViewModel.getMyRepositories(true, callBack = { rst ->
      list = rst
    }, finally = {
      CountingIdlingResourceSingleton.decrement()
    })
    CountingIdlingResourceSingleton.increment()
    composeTestRule.waitForIdle()

    if (!list.isNullOrEmpty()) {
      val firstItem = list!![0]
      composeTestRule.onNodeWithText(firstItem.fullName).assertIsDisplayed()

      val lastItem = list!!.last()
      composeTestRule.onNodeWithTag("mineLazyColumn", useUnmergedTree = true)
        .performScrollToIndex(list!!.size - 1)
      composeTestRule.onNodeWithText(lastItem.fullName).assertIsDisplayed()
    }
  }

  @OptIn(ExperimentalTestApi::class)
  @Test
  fun testSettingsPage() {
    // use your own valid accessToken to mock the signed in state
    DataStoreUtils.saveSyncStringData(
      DataStoreUtils.ACCESS_TOKEN,
      ownValidAccessToken
    )

    // click mine button and navigate to mine page
    composeTestRule.onNodeWithText(context.getString(R.string.mine)).performClick()
    // click optIcon button and navigate to settings page
    composeTestRule.onNodeWithTag("optIcon").performClick()

    composeTestRule.waitUntilAtLeastOneExists(hasText(context.getString(R.string.settings)), 3000)
    // use the getUserInfo api get the result and compare with the user info in the ui
    mineSettingViewModel = MineSettingViewModel(service)
    var user: UserInfoBean? = null
    mineSettingViewModel.getUserInfo(callBack = { it ->
      // wait until the page is ready to show
      user = it
    }, finally = {
      CountingIdlingResourceSingleton.decrement()
    })
    CountingIdlingResourceSingleton.increment()
    composeTestRule.waitForIdle()
    // wait until the page is ready to show
    composeTestRule.onNodeWithText(context.getString(R.string.settings)).assertIsDisplayed()
    composeTestRule.onNodeWithText("${user?.name}(${user?.login})", useUnmergedTree = true)
      .assertIsDisplayed()
    composeTestRule.onNodeWithText("followers:${user?.followers}", useUnmergedTree = true)
      .assertIsDisplayed()
    composeTestRule.onNodeWithText("following:${user?.following}", useUnmergedTree = true)
      .assertIsDisplayed()
    composeTestRule.onNodeWithText("company:${user?.company}", useUnmergedTree = true)
      .assertIsDisplayed()
    composeTestRule.onNodeWithText("email:${user?.email}", useUnmergedTree = true)
      .assertIsDisplayed()

    // click starred button and navigate to starred page
    composeTestRule.onNodeWithText(context.getString(R.string.starred)).assertIsDisplayed()
    composeTestRule.onNodeWithText(context.getString(R.string.starred)).performClick()
    // use the getStarredRepositories api get the result and compare with the starred repositories in the ui
    starredViewModel = StarredViewModel(service, eventBus)
    var list: List<RepositoryItem>? = null
    starredViewModel.getStarredRepositories(true, callBack = { rst ->
      list = rst
    }, finally = {
      CountingIdlingResourceSingleton.decrement()
    })
    CountingIdlingResourceSingleton.increment()
    composeTestRule.waitForIdle()

    if (!list.isNullOrEmpty()) {
      val firstItem = list!![0]
      composeTestRule.onNodeWithText(firstItem.fullName).assertIsDisplayed()

      val lastItem = list!!.last()
      composeTestRule.onNodeWithTag("starredLazyColumn", useUnmergedTree = true)
        .performScrollToIndex(list!!.size - 1)
      composeTestRule.onNodeWithText(lastItem.fullName).assertIsDisplayed()
    }
  }
}