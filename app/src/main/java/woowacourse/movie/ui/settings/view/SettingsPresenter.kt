package woowacourse.movie.ui.settings.view

import woowacourse.movie.data.BookedTicketDatabase
import woowacourse.movie.data.toBookedTicket
import woowacourse.movie.domain.model.BookedTicket
import java.time.LocalDateTime

class SettingsPresenter(
    private val view: SettingsContract.View,
) : SettingsContract.Presenter {
    private lateinit var db: BookedTicketDatabase

    override fun loadDatabase(database: BookedTicketDatabase) {
        db = database
    }

    override fun handleMovieNotificationByToggle(isChecked: Boolean) {
        if (isChecked) {
            // DB에서 알림 예약 가능한 모든 영화들을 예약
            val dao = db.bookedTicketDao()
            val availableNotificationTickets: List<BookedTicket> =
                dao.findAll().map { entity -> entity.toBookedTicket() }
                    .filter { bookedTicket ->
                        bookedTicket.movieSchedule.screeningDateTime
                            .isAfter(LocalDateTime.now().plusMinutes(30))
                    }
            view.notifyMovieReminderRegistered(availableNotificationTickets)
        } else {
            // 예약된 모든 영화 알림 해제
            view.notifyMovieReminderCleared()
        }
    }
}
