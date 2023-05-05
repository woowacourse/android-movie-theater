package woowacourse.movie.feature.alarm

import woowacourse.movie.data.AlarmSettingRepository
import woowacourse.movie.model.TicketsState

class AlarmReceiverPresenter(
    val view: AlarmReceiverContract.View,
    private val alarmSettingRepository: AlarmSettingRepository
) : AlarmReceiverContract.Presenter {
    override fun receiveAlarmSignal(tickets: TicketsState) {
        val isNotification = alarmSettingRepository.enablePushNotification
        if (isNotification.not()) return
        view.generateMovieAlarmNotification(tickets)
    }
}
