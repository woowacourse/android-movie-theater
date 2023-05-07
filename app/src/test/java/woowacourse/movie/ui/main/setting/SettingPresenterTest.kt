package woowacourse.movie.ui.main.setting

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.DefaultPreference

class SettingPresenterTest {
    private lateinit var view: SettingContract.View
    private lateinit var sharedPreferences: DefaultPreference
    private lateinit var presenter: SettingPresenter

    @Before
    fun setUp() {
        // given
        view = mockk(relaxed = true)
        sharedPreferences = mockk(relaxed = true)
        presenter = SettingPresenter(view, sharedPreferences)
    }

    @Test
    fun `값을 sharedPreference에서 가져온다`() {
        // given
        every { sharedPreferences.getBoolean("notifications", false) } returns true

        // when
        presenter.setUpSwitch("notifications", false)

        // then
        verify { sharedPreferences.getBoolean("notifications", false) }
        verify { view.setSwitchChecked(true) }
    }

    @Test
    fun `값을 설정하여 sharedPreference에 저장한다`() {
        // given
        every {
            sharedPreferences.setBoolean("notifications", true)
        } answers {
            println("sharedPreferences = ${sharedPreferences.getBoolean("notifications", false)}")
        }

        // when
        presenter.updateSwitch("notifications", true)

        // then
        verify { sharedPreferences.setBoolean("notifications", true) }
        verify { view.setSwitchChecked(true) }
    }
}
