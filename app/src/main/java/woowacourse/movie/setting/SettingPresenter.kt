package woowacourse.movie.setting

import woowacourse.movie.model.Reservation
import woowacourse.movie.repository.SettingRepository
import woowacourse.movie.setting.uimodel.ReservationAlarmUiModel
import woowacourse.movie.usecase.FetchAllReservationsUseCase
import java.util.concurrent.FutureTask

class SettingPresenter(
    private val view: SettingContract.View,
    private val fetchAllReservationsUseCase: FetchAllReservationsUseCase,
    private val settingRepository: SettingRepository,
) : SettingContract.Presenter {
    private lateinit var reservationAlarmUiModels: List<ReservationAlarmUiModel>

    override fun initSetting() {
        reservationAlarmUiModels =
            getReservationTimes().map {
                ReservationAlarmUiModel(it, 30)
            }
        val checked = settingRepository.getAlarmState()
        view.showChecked(checked)
    }

    override fun toggleAlarm() {
        val alarmChecked = settingRepository.getAlarmState()
        if (alarmChecked) {
            settingRepository.setAlarmState(false)
            view.showChecked(false)
            view.turnOffAlarm(reservationAlarmUiModels)
        } else {
            settingRepository.setAlarmState(true)
            view.showChecked(true)
            view.turnOnAlarm(reservationAlarmUiModels)
        }
    }

    private fun getReservationTimes(): List<Reservation> {
        val task = FutureTask { fetchAllReservationsUseCase() }
        Thread(task).start()
        task.get().onSuccess {
            return it
        }.onFailure {
            // view.showError()
        }
        return listOf()
    }
}
