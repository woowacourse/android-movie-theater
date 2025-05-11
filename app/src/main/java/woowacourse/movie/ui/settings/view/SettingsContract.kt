package woowacourse.movie.ui.settings.view

import woowacourse.movie.data.BookedTicketDatabase
import woowacourse.movie.domain.model.BookedTicket

interface SettingsContract {
    interface Presenter {
        fun loadDatabase(database: BookedTicketDatabase)
        fun handleMovieNotificationByToggle(isChecked: Boolean)
    }

    interface View {
        fun notifyMovieReminderRegistered(availableNotificationTickets : List<BookedTicket>)
        fun notifyMovieReminderCleared()
    }

}
