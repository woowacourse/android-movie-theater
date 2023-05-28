package woowacourse.movie.presentation.settings

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.model.data.local.SettingPreference

@RunWith(AndroidJUnit4::class)
@LargeTest
class SettingsFragmentTest {

    private lateinit var mockSettingsFragmentFactory: MockSettingsFragmentFactory
    private lateinit var scenario: FragmentScenario<SettingsFragment>

    private class MockSettingsFragmentFactory : FragmentFactory() {
        val mockSettingStorage = mockk<SettingPreference>()

        override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
            return when (className) {
                SettingsFragment::class.java.name -> {
                    SettingsFragment(getSettingStorage = { mockSettingStorage })
                }
                else -> super.instantiate(classLoader, className)
            }
        }
    }

    @Test
    fun `기존_설정_값이_true일때_스위치_버튼을_누르면_false가_저장된다`() {
        // given
        mockSettingsFragmentFactory = MockSettingsFragmentFactory()

        every { mockSettingsFragmentFactory.mockSettingStorage.getNotificationSettings() } returns true
        val slot = slot<Boolean>()
        every { mockSettingsFragmentFactory.mockSettingStorage.setNotificationSettings(capture(slot)) } returns true

        scenario =
            launchFragmentInContainer<SettingsFragment>(
                factory = mockSettingsFragmentFactory,
                themeResId = R.style.Theme_Movie
            )

        // when
        onView(withId(R.id.switchPushPermission)).perform(click())

        // then
        val expected = false
        val actual = slot.captured
        assertEquals(expected, actual)
    }

    @Test
    fun `기존_설정_값이_false일때_스위치_버튼을_누르면_true가_저장된다`() {
        // given
        mockSettingsFragmentFactory = MockSettingsFragmentFactory()

        every { mockSettingsFragmentFactory.mockSettingStorage.getNotificationSettings() } returns false
        val slot = slot<Boolean>()
        every { mockSettingsFragmentFactory.mockSettingStorage.setNotificationSettings(capture(slot)) } returns true

        scenario =
            launchFragmentInContainer<SettingsFragment>(
                factory = mockSettingsFragmentFactory,
                themeResId = R.style.Theme_Movie
            )

        // when
        onView(withId(R.id.switchPushPermission)).perform(click())

        // then
        val expected = true
        val actual = slot.captured
        assertEquals(expected, actual)
    }
}
