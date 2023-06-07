package woowacourse.movie.presentation.activities.main.fragments.theaterPicker

import woowacourse.movie.presentation.model.item.Movie
import woowacourse.movie.presentation.model.item.Theater

class TheaterPickerDialogPresenter(
    val view: TheaterPickerDialogContract.View,
    val movie: Movie,
) : TheaterPickerDialogContract.Presenter {

    override fun moveTicketingActivity(theater: Theater) {
        view.startTicketingActivity(theater)
    }
}
