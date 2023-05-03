package woowacourse.movie.ui.fragment.setting

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import woowacourse.movie.DefaultPreference

class SettingPresenterTest {
    @Test
    fun `값을 sharedPreference에서 가져온다`() {
        // given
        val view: SettingContract.View = mockk(relaxed = true)
        val sharedPreferences: DefaultPreference = mockk(relaxed = true)
        val presenter = SettingPresenter(
            view,
            sharedPreferences
        )
        every { sharedPreferences.getBoolean("notifications", false) } returns true

        // when
        presenter.getBoolean("notifications", false)

        // then
        verify { sharedPreferences.getBoolean("notifications", false) }
        verify { view.setSwitchChecked(true) }
    }

    @Test
    fun `값을 설정하여 sharedPreference에 저장한다`() {
        // given
        val view: SettingContract.View = mockk(relaxed = true)
        val sharedPreferences: DefaultPreference = mockk(relaxed = true)
        val presenter = SettingPresenter(
            view,
            sharedPreferences
        )
        every {
            sharedPreferences.setBoolean("notifications", true)
        } answers {
            println("sharedPreferences = ${sharedPreferences.getBoolean("notifications", false)}")
        }

        // when
        presenter.setBoolean("notifications", true)

        // then
        verify { sharedPreferences.setBoolean("notifications", true) }
        verify { view.setSwitchChecked(true) }
    }
}
