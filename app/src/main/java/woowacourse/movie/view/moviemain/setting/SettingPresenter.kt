package woowacourse.movie.view.moviemain.setting

import woowacourse.movie.data.reservation.ReservationMockRepository
import woowacourse.movie.data.setting.SettingDataManager
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.view.mapper.toUiModel
import woowacourse.movie.view.model.AlarmManageable
import woowacourse.movie.view.moviemain.setting.SettingFragment.Companion.ALARM_MINUTE_INTERVAL

class SettingPresenter(
    private val view: SettingContract.View,
    private val settingManager: SettingDataManager,
    private val alarmController: AlarmManageable,
) : SettingContract.Presenter {

    override fun initToggle() {
        view.setToggle(settingManager.getIsAlarmSetting())
    }

    override fun onClick(isOn: Boolean) {
        if (isOn && view.requestNotificationPermission()) {
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
}
