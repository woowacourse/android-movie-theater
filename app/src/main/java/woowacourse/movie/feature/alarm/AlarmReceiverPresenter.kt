package woowacourse.movie.feature.alarm

import com.example.domain.repository.AlarmSettingRepository
import woowacourse.movie.model.TicketsState

class AlarmReceiverPresenter(
    val view: AlarmReceiverContract.View,
    private val alarmSettingRepository: AlarmSettingRepository
) : AlarmReceiverContract.Presenter {
    override fun receiveAlarmSignal(tickets: TicketsState) {
        val isNotification = alarmSettingRepository.getEnablePushNotification()
        if (isNotification.not()) return
        view.generateMovieAlarmNotification(tickets)
    }
}
