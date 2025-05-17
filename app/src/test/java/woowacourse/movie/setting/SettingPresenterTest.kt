package woowacourse.movie.setting

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.SettingRepository

class SettingPresenterTest {
    private lateinit var presenter: SettingPresenter
    private val mockView = mockk<SettingContract.View>(relaxed = true)
    private val mockRepository = mockk<SettingRepository>(relaxed = true)

    @BeforeEach
    fun setUp() {
        presenter = SettingPresenter(mockView, mockRepository)
    }

    @Test
    fun `저장소에서 권한 여부를 가져와 초기화한다`() {
        // given
        val isGranted = slot<Boolean>()
        every { mockRepository.isAlarmPermitted() } returns true
        every { mockView.initAlarmState(capture(isGranted)) } just Runs

        // when
        presenter.setPermissionState()

        // then
        verify { mockView.initAlarmState(any()) }

        assertThat(isGranted.captured).isTrue()
    }

    @Test
    fun `저장소에 권한 상태를 저장한다`() {
        // given
        val isGranted = slot<Boolean>()
        every { mockRepository.setAlarmPermitted(capture(isGranted)) } just Runs

        // when
        val permission = true
        presenter.updatePermission(permission)

        // then
        verify { mockRepository.setAlarmPermitted(any()) }

        assertThat(isGranted.captured).isEqualTo(permission)
    }
}
