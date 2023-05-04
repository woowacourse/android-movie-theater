package woowacourse.movie.view.moviemain.setting

import woowacourse.movie.data.reservation.ReservationMockRepository
import woowacourse.movie.data.setting.SettingDataManager
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.view.mapper.toUiModel

class SettingPresenter(
    private val view: SettingContract.View,
    private val settingManager: SettingDataManager,
) : SettingContract.Presenter {

    override fun initToggle() {
        view.setToggle(settingManager.getIsAlarmSetting())
    }

    override fun onClick(isOn: Boolean) {
        if (isOn && view.requestNotificationPermission()) {
            val reservationRepo: ReservationRepository = ReservationMockRepository
            val reservations = reservationRepo.findAll().map { it.toUiModel() }
            view.setAlarms(reservations)
            settingManager.setIsAlarmSetting(true)
            view.setToggle(true)
            return
        }
        view.cancelAlarms()
        settingManager.setIsAlarmSetting(false)
        view.setToggle(false)
    }
}
