package woowacourse.movie.ui.main.setting

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import org.junit.Before
import org.junit.Test
import woowacourse.movie.repository.SettingRepository

class SettingPresenterTest {
    private lateinit var presenter: SettingContract.Presenter
    private lateinit var settingRepository: SettingRepository

    @Before
    fun init() {
        settingRepository = mockk()
        presenter = SettingPresenter(
            settingRepository
        )
    }

    @Test
    fun `저장된_알림_상태를_잘 전달하는지_확인`() {
        every { settingRepository.getNotificationState() } returns true

        val expected = true
        val actual = presenter.getNotificationState()
        assert(expected == actual)
    }

    @Test
    fun `스위치의_상태를_false로_설정할_때_repository에_false를_전달하는지_확인`() {
        val slot = slot<Boolean>()
        // every: 이 메소드를 호출 할 때마다 시행하겠다는 뜻
        every { settingRepository.setNotificationState(capture(slot)) } just runs

        presenter.setSwitchState(false)

        val expected = false
        val actual = slot.captured
        assert(expected == actual)
    }
}
