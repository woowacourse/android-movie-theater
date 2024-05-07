package woowacourse.movie.presentation.ui.main.home.bottom

import woowacourse.movie.domain.repository.ScreenRepository

class BottomTheaterPresenter(
    val view: BottomTheaterContract.View,
    val repository: ScreenRepository,
) : BottomTheaterContract.Presenter {
    override fun fetchTheaterCounts(movieId: Int) {
        repository.findTheaterCount(movieId = movieId).onSuccess { theaterCounts ->
            view.showBottomTheater(theaterCounts, movieId)
        }
    }
}
