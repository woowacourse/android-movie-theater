package woowacourse.movie.ui.home

import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.domain.repository.TheaterRepository

class TheaterBottomSheetPresenter(
    private val view: TheatersBottomSheetContract.View,
    private val screenRepository: ScreenRepository,
    private val theaterRepository: TheaterRepository,
) : TheatersBottomSheetContract.Presenter {
    private var screenId: Int = 0

    override fun saveScreenId(screenId: Int) {
        this.screenId = screenId

        view.initTheaterAdapter(screenRepository.findById(screenId).getOrThrow())
    }

    override fun loadTheaters() {
        val screen = screenRepository.findById(screenId).getOrThrow()
        val theaters = theaterRepository.loadAll()

        view.showTheaters(screen, theaters.screeningTheater(screen))
    }

    override fun onTheaterSelected(theaterId: Int) {
        view.navigateToScreenDetail(screenId, theaterId)
    }
}
