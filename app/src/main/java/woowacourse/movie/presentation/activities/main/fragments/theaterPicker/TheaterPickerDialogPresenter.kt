package woowacourse.movie.presentation.activities.main.fragments.theaterPicker

import woowacourse.movie.presentation.model.item.Movie

class TheaterPickerDialogPresenter(
    val view: TheaterPickerDialogContract.View,
) : TheaterPickerDialogContract.Presenter {

    override fun moveTicketingActivity(movie: Movie) {
        view.startTicketingActivity(movie)
    }
}
