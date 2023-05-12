package woowacourse.movie.ui.main.setting

import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.SharedPreference

class SettingPresenterTest {
    private lateinit var view: SettingContract.View
    private lateinit var sharedPreferences: SharedPreference
    private lateinit var presenter: SettingPresenter

    @Before
    fun setUp() {
        // given
        view = mockk(relaxed = true)
        sharedPreferences = object : SharedPreference {
            override fun getBoolean(key: String, defValue: Boolean): Boolean = true

            override fun setBoolean(key: String, value: Boolean) = Unit
        }
        presenter = SettingPresenter(view, sharedPreferences)
    }

    @Test
    fun `값을 sharedPreference에서 가져온다`() {
        // when
        presenter.setUpSwitch("notifications", true)

        // then
        verify { view.setSwitchChecked(true) }
    }

    @Test
    fun `값을 설정하여 sharedPreference에 저장한다`() {
        // when
        presenter.updateSwitch("notifications", true)

        // then
        verify { view.setSwitchChecked(true) }
    }
}
