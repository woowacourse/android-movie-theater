package woowacourse.movie.ui.main.setting

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Before
import org.junit.Test
import woowacourse.movie.repository.SettingRepository

class SettingPresenterTest {
    private lateinit var view: SettingContract.View
    private lateinit var presenter: SettingContract.Presenter
    private lateinit var settingRepository: SettingRepository

    @Before
    fun init() {
        view = mockk()
        settingRepository = mockk()
        presenter = SettingPresenter(
            view,
            settingRepository
        )
    }

    @Test
    fun `스위치의_상태를_false로_설정할_때_repository에_false를_전달하는지_확인`() {
        var state = true

        val slot = slot<Boolean>()
        every { settingRepository.setNotificationState(capture(slot)) } answers {
            state = slot.captured
        }

        presenter.setSwitchState(false)

        val expected = false
        val actual = state
        assert(expected == actual)
    }
}
