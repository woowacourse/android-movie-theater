package woowacourse.movie.setting

import woowacourse.movie.model.Reservation
import woowacourse.movie.setting.uimodel.ReservationAlarmUiModel
import woowacourse.movie.usecase.FetchAllReservationsUseCase
import java.util.concurrent.FutureTask

class SettingPresenter(
    private val view: SettingContract.View,
    private val fetchAllReservationsUseCase: FetchAllReservationsUseCase,
) : SettingContract.Presenter {
    private var alarmChecked: Boolean = false
    private lateinit var reservationAlarmUiModels: List<ReservationAlarmUiModel>

    override fun initSetting(checked: Boolean) {
        alarmChecked = checked
        reservationAlarmUiModels =
            getReservationTimes().map {
                ReservationAlarmUiModel(it, 30)
            }
    }

    override fun toggleAlarm() {
        if (alarmChecked) {
            alarmChecked = false
            view.turnOffAlarm(reservationAlarmUiModels)
        } else {
            alarmChecked = true
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
