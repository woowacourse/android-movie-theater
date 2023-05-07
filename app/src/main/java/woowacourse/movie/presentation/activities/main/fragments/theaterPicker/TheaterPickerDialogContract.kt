package woowacourse.movie.presentation.activities.main.fragments.theaterPicker

import woowacourse.movie.presentation.model.item.Theater

interface TheaterPickerDialogContract {
    interface View {
        val presenter: Presenter

        fun startTicketingActivity(theater: Theater)
    }

    interface Presenter {
        fun moveTicketingActivity(theater: Theater)
    }
}
