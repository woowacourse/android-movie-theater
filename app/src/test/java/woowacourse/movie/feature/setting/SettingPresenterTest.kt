package woowacourse.movie.feature.setting

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.setting.AlarmSetting
import woowacourse.movie.util.permission.PermissionChecker

class SettingPresenterTest {
    private val view: SettingContract.View = mockk()
    private val alarmSetting: AlarmSetting = mockk()
    private val permissionChecker: PermissionChecker = mockk()

    private lateinit var sut: SettingContract.Presenter // System Under Test

    @Before
    fun setUp() {
        sut = SettingPresenter(view, alarmSetting, permissionChecker)
    }

    @Test
    fun `영화 시작 알림 설정이 켜져 있는 경우 설정 화면의 스위치를 on한다`() {
        // given
        every { alarmSetting.isEnable } returns true
        every { view.setMovieReminderChecked(any()) } just runs
        every { permissionChecker.hasPermission } returns true

        // when
        sut.setMovieReminderChecked()

        // then
        verify { view.setMovieReminderChecked(true) }
    }

    @Test
    fun `영화 시작 알림 설정이 꺼져 있는 경우 설정 화면의 스위치를 off한다`() {
        // given
        every { alarmSetting.isEnable } returns false
        every { view.setMovieReminderChecked(any()) } just runs
        every { permissionChecker.hasPermission } returns true

        // when
        sut.setMovieReminderChecked()

        // then
        verify { view.setMovieReminderChecked(false) }
    }

    @Test
    fun `앱 권한이 없다면 설정 화면의 스위치를 off하고, 앱 권한을 요청한다`() {
        // given
        every { alarmSetting.isEnable } returns false
        every { permissionChecker.hasPermission } returns false
        every { view.setMovieReminderChecked(any()) } just runs
        every { alarmSetting.isEnable = any() } just runs
        every { view.requestPermission() } just runs

        // when
        sut.setMovieReminderChecked()

        // then
        verifyRequestPermission()
    }

    @Test
    fun `설정 화면의 스위치를 on했을 때, 앱 권한이 있다면, 영화 시작 알림 설정을 on한다`() {
        // given
        every { permissionChecker.hasPermission } returns true
        every { alarmSetting.isEnable = any() } just runs

        // when
        sut.changeMovieReminderChecked(true)

        // then
        verify { alarmSetting.isEnable = true }
    }

    @Test
    fun `설정 화면의 스위치를 off했을 때, 앱 권한이 있다면, 영화 시작 알림 설정을 off한다`() {
        // given
        every { permissionChecker.hasPermission } returns true
        every { alarmSetting.isEnable = any() } just runs

        // when
        sut.changeMovieReminderChecked(false)

        // then
        verify { alarmSetting.isEnable = false }
    }

    @Test
    fun `설정 화면의 스위치를 on했을 때, 앱 권한이 없다면, 권한을 요청한다`() {
        // given
        every { permissionChecker.hasPermission } returns false
        every { alarmSetting.isEnable = any() } just runs
        every { view.setMovieReminderChecked(any()) } just runs
        every { view.requestPermission() } just runs

        // when
        sut.changeMovieReminderChecked(true)

        // then
        verifyRequestPermission()
    }

    private fun verifyRequestPermission() {
        verify { alarmSetting.isEnable = false }
        verify { view.setMovieReminderChecked(false) }
        verify { view.requestPermission() }
    }
}
