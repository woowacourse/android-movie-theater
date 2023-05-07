package woowacourse.movie.ui.setting

import woowacourse.movie.data.alarm.AlarmStateRepository
import woowacourse.movie.data.reservation.ReservationRepository

class SettingPresenter(
    private val view: SettingContract.View,
    private val alarmStateRepository: AlarmStateRepository,
    private val reservationRepository: ReservationRepository,
) : SettingContract.Presenter {
    override fun checkSwitchState() {
        view.setToggleButton(alarmStateRepository.getData())
    }

    override fun clickSwitch(isChecked: Boolean) {
        alarmStateRepository.saveData(isChecked)
        checkSwitchState()

        val tickets = reservationRepository.getData()
        if (isChecked) {
            tickets.forEach { view.setAlarms(it) }
        } else {
            tickets.forEach { view.cancelAlarms(it) }
        }
    }
}
