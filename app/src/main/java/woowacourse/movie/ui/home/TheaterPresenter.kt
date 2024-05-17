package woowacourse.movie.ui.home

import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.domain.repository.TheaterRepository

class TheaterPresenter(
    private val view: TheaterContract.View,
    private val screenRepository: ScreenRepository,
    private val theaterRepository: TheaterRepository,
) : TheaterContract.Presenter {
    private var screenId: Int = 0
    private lateinit var loadedScreen: Screen

    override fun saveScreenId(screenId: Int) {
        this.screenId = screenId
        loadedScreen = screenRepository.findById(screenId).getOrThrow()
    }

    override fun initTheaterAdapter() {
        screenRepository.findById(screenId).getOrThrow()

        view.initTheaterAdapter(loadedScreen)
    }

    override fun loadTheaters() {
        val theaters = theaterRepository.loadAll()

        view.showTheaters(loadedScreen, theaters.screeningTheater(loadedScreen.movie))
    }
}
