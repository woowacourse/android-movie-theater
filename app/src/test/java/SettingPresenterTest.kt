import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.DataRepository
import woowacourse.movie.fragment.setting.SettingContract
import woowacourse.movie.fragment.setting.SettingPresenter

class SettingPresenterTest {

    private lateinit var mockView: SettingContract.View
    private lateinit var mockRepository: DataRepository
    private lateinit var presenter: SettingPresenter

    @Before
    fun setUp() {
        mockView = mockk(relaxed = true)
        mockRepository = mockk()
        presenter = SettingPresenter(mockView, mockRepository)
    }

    @Test
    fun `스위치 값을 저장할 수 있다`() {
        // given
        val isChecked = true
        every { mockRepository.setBooleanValue(isChecked) } just Runs

        // when
        presenter.saveCanPushAlarmSwitchData(isChecked)

        // then
        verify { mockRepository.setBooleanValue(isChecked) }
    }

    @Test
    fun `스위치 값을 저장한 곳으로 부터 가져올 수 있다`() {
        // given
        val default = true
        val isChecked = false
        every { mockRepository.getBooleanValue(default) } returns isChecked
        every { mockView.updateCanPushSwitch(isChecked) } just Runs

        // when
        presenter.loadCanPushAlarmSwitchData(default)

        // then
        verify { mockView.updateCanPushSwitch(isChecked) }
    }

    @Test
    fun `푸시 알람 허용 스위치가 false이면 해당 데이터를 저장만 한다`() {
        // given
        val isPermissionDenied = true
        val isForeverDeniedPermission = true
        val isChecked = false
        every { presenter.saveCanPushAlarmSwitchData(isChecked) } just runs

        // when
        presenter.onClickCanPushSwitch(isPermissionDenied, isForeverDeniedPermission, isChecked)

        // then
        verify { presenter.saveCanPushAlarmSwitchData(isChecked) }
        verify(inverse = true) { mockView.reRequestPermission(isChecked) }
        verify(inverse = true) { mockView.disableCanPushSwitch() }
    }

    @Test
    fun `푸시알람 스위치가 true이고 권한을 거절한적이 있지만 영구거절은 아닐경우 권한을 요청한다`() {
        // given
        val isPermissionDenied = true
        val isForeverDeniedPermission = false
        val isChecked = true
        every { presenter.saveCanPushAlarmSwitchData(isChecked) } just runs
        every { mockView.reRequestPermission(isChecked) } just Runs

        // when
        presenter.onClickCanPushSwitch(isPermissionDenied, isForeverDeniedPermission, isChecked)

        // then
        verify { presenter.saveCanPushAlarmSwitchData(isChecked) }
        verify { mockView.reRequestPermission(isChecked) }
        verify(inverse = true) { mockView.disableCanPushSwitch() }
    }

    @Test
    fun `푸시 알람 스위치가 false이고 권한을 거절하지 않았다면 스위치의 값을 저장한다`() {
        // given
        val isPermissionDenied = false
        val isForeverDeniedPermission = false
        val isChecked = false
        every { presenter.saveCanPushAlarmSwitchData(isChecked) } just runs
        every { mockView.reRequestPermission(isChecked) } just runs

        // when
        presenter.onClickCanPushSwitch(isPermissionDenied, isForeverDeniedPermission, isChecked)

        // then
        verify { presenter.saveCanPushAlarmSwitchData(isChecked) }
        verify(inverse = true) { mockView.reRequestPermission(isChecked) }
        verify(inverse = true) { mockView.disableCanPushSwitch() }
    }
}
