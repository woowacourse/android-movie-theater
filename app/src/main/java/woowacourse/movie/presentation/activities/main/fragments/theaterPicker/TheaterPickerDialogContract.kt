package woowacourse.movie.presentation.activities.main.fragments.theaterPicker

import woowacourse.movie.presentation.model.item.Movie

interface TheaterPickerDialogContract {
    interface View {
        val presenter: Presenter

        fun startTicketingActivity(movie: Movie)
    }

    interface Presenter {
        fun moveTicketingActivity(movie: Movie)
    }
}
