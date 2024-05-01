package woowacourse.movie.presentation.view.screening.theater

import woowacourse.movie.presentation.repository.TheaterRepository
import woowacourse.movie.repository.TheaterRepositoryImpl

class TheaterBottomSheetPresenterImpl(private val view: TheaterBottomSheetContract.View) :
    TheaterBottomSheetContract.Presenter {
    private val theaterRepository: TheaterRepository = TheaterRepositoryImpl

    override fun loadTheaters(movieId: Int) {
        val theatersInfo = theaterRepository.theatersInfo(movieId)
        view.showTheaterInfo(theatersInfo)
    }

    override fun onTheaterClicked(theaterId: Int) {

    }
}
