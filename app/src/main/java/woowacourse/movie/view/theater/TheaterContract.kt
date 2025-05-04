package woowacourse.movie.view.theater

import woowacourse.movie.view.model.TheaterUIModel

interface TheaterContract {
    interface View {
        fun showTheaters(theaters: List<TheaterUIModel>)

        fun showEmptySlotMessage()

        fun navigateToReservation(theaterUIModel: TheaterUIModel)
    }

    interface Presenter {
        fun fetchTheaters()

        fun theaterSelected(theaterUIModel: TheaterUIModel)
    }
}
