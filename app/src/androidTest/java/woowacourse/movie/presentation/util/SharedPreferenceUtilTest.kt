package woowacourse.movie.presentation.util

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.MovieTheaterApplication

@RunWith(AndroidJUnit4::class)
class SharedPreferenceUtilTest {

    @Before
    fun clearPreference() {
        MovieTheaterApplication.settingsPreference.edit().remove("notification").apply()
    }

    @Test
    fun `푸시_알림_설정_저장소의_true를_저장한다면_true값을_돌려받을수_있다`() {
        SharedPreferenceUtil.setNotificationSettings(true)

        val expected = true
        val actual = SharedPreferenceUtil.getNotificationSettings()
        assertEquals(expected, actual)
    }

    @Test
    fun `푸시_알림_설정_저장소의_false_저장한다면_false값을_돌려받을수_있다`() {
        SharedPreferenceUtil.setNotificationSettings(false)

        val expected = false
        val actual = SharedPreferenceUtil.getNotificationSettings()
        assertEquals(expected, actual)
    }

    @Test
    fun `푸시_알림_설정_저장소의_값을_설정하지_않을경우_true값을_돌려받을수_있다`() {
        val expected = true
        val actual = SharedPreferenceUtil.getNotificationSettings()
        assertEquals(expected, actual)
    }
}
