package woowacourse.movie.feature.alarm

import com.example.domain.usecase.LoadAlarmSettingInfoUseCase
import woowacourse.movie.model.TicketsState

class AlarmReceiverPresenter(
    val view: AlarmReceiverContract.View,
    private val loadAlarmSettingInfoUseCase: LoadAlarmSettingInfoUseCase
) : AlarmReceiverContract.Presenter {
    override fun receiveAlarmSignal(tickets: TicketsState) {
        loadAlarmSettingInfoUseCase(
            onSuccess = { isNotification ->
                if (isNotification.not()) return@loadAlarmSettingInfoUseCase
                view.generateMovieAlarmNotification(tickets)
            },
            onFailure = { }
        )
    }
}
