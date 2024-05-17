package woowacourse.movie.presentation.screening.theater

import woowacourse.movie.domain.repository.TheaterRepository
import woowacourse.movie.data.repository.TheaterRepositoryImpl

class TheaterBottomSheetPresenterImpl(
    private val view: TheaterBottomSheetContract.View,
    private val movieId: Int,
) : TheaterBottomSheetContract.Presenter {
    private val theaterRepository: TheaterRepository = TheaterRepositoryImpl

    override fun loadTheaters() {
        val theatersInfo = theaterRepository.theatersInfo(movieId)
        view.showTheaterInfo(theatersInfo)
    }
}
