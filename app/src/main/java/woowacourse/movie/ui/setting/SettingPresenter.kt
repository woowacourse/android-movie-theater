package woowacourse.movie.ui.setting

import woowacourse.movie.data.alarm.AlarmStateRepository
import woowacourse.movie.uimodel.ReservationModel

class SettingPresenter(
    private val view: SettingContract.View,
    private val repository: AlarmStateRepository,
) : SettingContract.Presenter {
    override fun checkSwitchState() {
        view.setToggleButton(repository.getData())
    }

    override fun clickSwitch(isChecked: Boolean) {
        repository.saveData(isChecked)
        checkSwitchState()

        val tickets = ReservationModel.tickets
        if (isChecked) {
            tickets.forEach { view.setAlarms(it) }
        } else {
            tickets.forEach { view.cancelAlarms(it) }
        }
    }
}
