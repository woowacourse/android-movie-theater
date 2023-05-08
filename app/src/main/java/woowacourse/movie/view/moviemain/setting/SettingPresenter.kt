package woowacourse.movie.view.moviemain.setting

import woowacourse.movie.AlarmPreference
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.view.mapper.toUiModel

class SettingPresenter(
    private val view: SettingContract.View,
    private val alarmPreference: AlarmPreference,
    private val reservationRepository: ReservationRepository
) : SettingContract.Presenter {

    override fun loadAlarmSetting() {
        view.setToggleState(alarmPreference.isAlarmOn(false))
        view.initToggleState(alarmPreference.isAlarmOn(false))
    }

    override fun setAlarmPreference(isAlarmOn: Boolean) {
        alarmPreference.setIsAlarmOn(isAlarmOn)
        view.setToggleState(isAlarmOn)
    }

    override fun resetAlarms() {
        val reservations = reservationRepository.findAll().map { it.toUiModel() }
        alarmPreference.setIsAlarmOn(true)
        view.resetAlarms(reservations, ALARM_MINUTE_INTERVAL)
    }

    companion object {
        const val ALARM_MINUTE_INTERVAL = 30L
    }
}
