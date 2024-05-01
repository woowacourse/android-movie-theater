package woowacourse.movie.screeningmovie.theaters

interface TheaterContract {

    interface View {
        fun showTheaters(theaterUiModels: List<TheaterUiModel>)

        fun navigateSelectSeat()
    }

    interface Presenter {

        fun loadTheaters(movieId:Long)

        fun selectTheater(movieId: Long, theaterId: Long)
    }

}
