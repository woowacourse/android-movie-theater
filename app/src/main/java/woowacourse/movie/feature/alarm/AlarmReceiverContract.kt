package woowacourse.movie.feature.alarm

import woowacourse.movie.model.TicketsState

interface AlarmReceiverContract {
    interface View {
        fun generateMovieAlarmNotification(tickets: TicketsState)
    }

    interface Presenter {
        fun receiveAlarmSignal(tickets: TicketsState)
    }
}
