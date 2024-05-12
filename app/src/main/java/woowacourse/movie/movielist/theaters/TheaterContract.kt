package woowacourse.movie.movielist.theaters

interface TheaterContract {
    interface View {
        fun showTheaters(theaterUiModels: List<TheaterUiModel>)

        fun navigateToMovieDetail(
            movieId: Long,
            theaterId: Long,
        )
    }

    interface Presenter {
        fun loadTheaters(movieId: Long)

        fun selectTheater(
            movieId: Long,
            theaterId: Long,
        )
    }
}
