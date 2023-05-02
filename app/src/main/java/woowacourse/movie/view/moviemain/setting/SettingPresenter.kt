package woowacourse.movie.view.moviemain.setting

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import woowacourse.movie.data.reservation.ReservationMockRepository
import woowacourse.movie.data.setting.SettingDataManager
import woowacourse.movie.data.setting.SettingPreferencesManager
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.view.AlarmController
import woowacourse.movie.view.mapper.toUiModel
import woowacourse.movie.view.moviemain.setting.SettingFragment.Companion.ALARM_MINUTE_INTERVAL

class SettingPresenter(private val view: SettingContract.View, private val context: Context) : SettingContract.Presenter {
    private val settingManager: SettingDataManager = SettingPreferencesManager(context)
    private val alarmController: AlarmController = AlarmController(context)

    override fun initToggle() {
        view.setToggle(settingManager.getIsAlarmSetting())
    }

    override fun onClick(isOn: Boolean) {
        if (isOn && requestNotificationPermission()) {
            val reservationRepo: ReservationRepository = ReservationMockRepository
            val reservations = reservationRepo.findAll().map { it.toUiModel() }
            alarmController.registerAlarms(reservations, ALARM_MINUTE_INTERVAL)
            settingManager.setIsAlarmSetting(true)
            view.setToggle(true)
            return
        }
        alarmController.cancelAlarms()
        settingManager.setIsAlarmSetting(false)
        view.setToggle(false)
    }

    override fun requestNotificationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                view.requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                return ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
            }
        }
        return true // android 12 version 이하는 notification 권한 필요 없기 때문
    }
}
