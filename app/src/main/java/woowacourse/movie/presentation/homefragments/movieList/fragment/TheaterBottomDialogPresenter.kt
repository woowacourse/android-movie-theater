package woowacourse.movie.presentation.homefragments.movieList.fragment

import woowacourse.movie.repository.TheaterListRepository

class TheaterBottomDialogPresenter(
    private val view: TheaterBottomDialogContract.View,
    private val repository: TheaterListRepository,
) : TheaterBottomDialogContract.Presenter {
    override fun loadTheaters(movieId: Long) {
        val theaterList = repository.findTheaterList(movieId)
        view.showTheaterList(theaterList)
    }
}
