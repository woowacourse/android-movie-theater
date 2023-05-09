package woowacourse.movie.view.reservationcompleted

import woowacourse.movie.domain.repository.SettingRepository
import woowacourse.movie.view.model.ReservationUiModel

class ReservationCompletedPresenter(private val view: ReservationCompletedContract.View, private val settingManager: SettingRepository) : ReservationCompletedContract.Presenter {
    override fun decideAlarm(reservation: ReservationUiModel) {
        if (settingManager.getIsAlarmSetting()) view.registerAlarm(reservation)
    }
    override fun setAlarm(isOn: Boolean) {
        settingManager.setIsAlarmSetting(isOn)
    }
}
