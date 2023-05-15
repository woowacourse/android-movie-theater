package woowacourse.movie.feature.setting

import com.example.domain.repository.AlarmSettingRepository
import com.example.domain.usecase.LoadAlarmSettingInfoUseCase
import com.example.domain.usecase.SetAlarmSettingUseCase
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test

internal class SettingPresenterTest {
    private lateinit var view: SettingContract.View
    private lateinit var presenter: SettingContract.Presenter
    private lateinit var settingRepository: AlarmSettingRepository

    @Before
    fun init() {
        view = mockk()
        settingRepository = mockk()
        presenter = SettingPresenter(
            view,
            LoadAlarmSettingInfoUseCase(settingRepository),
            SetAlarmSettingUseCase(settingRepository)
        )
    }

    @Test
    fun 알람설정에_저장된_값을_불러와서_뷰에게_넘겨준다() {
        every { settingRepository.getEnablePushNotification() } returns true
        val slot = slot<Boolean>()
        every { view.setInitAlarmSettingInfo(capture(slot)) } just Runs // 뷰의 해당 메소드 호출시 넘겨받는 값을 캡쳐

        // 실행
        presenter.loadAlarmSettingInfo()

        val actual = slot.captured
        val expected = true
        assert(actual == expected)
    }

    @Test
    fun 스위치의_체크_상태가_변경됐을때_알람권한true_바뀐체크값true이면_레포지토리에_바뀐체크값이_true로_저장된다() {
        var initAlarmState = false
        every { settingRepository.getEnablePushNotification() } answers {
            initAlarmState // 동적으로 해당 지역변수를 리턴하도록 지정
        }

        val slot = slot<Boolean>()
        every { settingRepository.setEnablePushNotification(capture(slot)) } answers {
            initAlarmState = slot.captured // 설정한값을 캡쳐하고 그 값으로 initAlarmState 지역변수 초기화
        }

        // 실행
        presenter.changeSwitchCheckedState(isPermissionApprove = true, isSwitchChecked = true)

        verify(exactly = 0) { view.alarmPermissionIsNotApprove() } // 해당 메소드는 호출되지 않는다
        verify { settingRepository.setEnablePushNotification(any()) } // 호출됐을 것이다.
        val expected = true
        assert(settingRepository.getEnablePushNotification() == expected)
    }

    @Test
    fun 스위치의_체크_상태가_변경됐을때_알람권한true_바뀐체크값false이면_레포지토리에_바뀐체크값이_false로_저장된다() {
        var initAlarmState = true
        every { settingRepository.getEnablePushNotification() } answers {
            initAlarmState // 동적으로 해당 지역변수를 리턴하도록 지정
        }

        val slot = slot<Boolean>()
        every { settingRepository.setEnablePushNotification(capture(slot)) } answers {
            initAlarmState = slot.captured // 설정한값을 캡쳐하고 그 값으로 initAlarmState 지역변수 초기화
        }

        // 실행
        presenter.changeSwitchCheckedState(isPermissionApprove = true, isSwitchChecked = false)

        verify(exactly = 0) { view.alarmPermissionIsNotApprove() } // 해당 메소드는 호출되지 않는다
        verify { settingRepository.setEnablePushNotification(any()) } // 호출됐을 것이다.
        val expected = false
        assert(settingRepository.getEnablePushNotification() == expected)
    }

    @Test
    fun 스위치의_체크_상태가_변경됐을때_알람권한false이면_뷰의_알람권한이_허용되지않았음을_나타내는_메소드가_호출된다() {
        every { view.alarmPermissionIsNotApprove() } just Runs

        var initAlarmState = false
        every { settingRepository.getEnablePushNotification() } answers {
            initAlarmState // 동적으로 해당 지역변수를 리턴하도록 지정
        }

        val slot = slot<Boolean>()
        every { settingRepository.setEnablePushNotification(capture(slot)) } answers {
            initAlarmState = slot.captured // 설정한값을 캡쳐하고 그 값으로 initAlarmState 지역변수 초기화
        }

        // 실행
        presenter.changeSwitchCheckedState(isPermissionApprove = false, isSwitchChecked = true)

        // 검사
        verify { view.alarmPermissionIsNotApprove() } // 해당 메소드가 호출된 적이 있다
    }
}
