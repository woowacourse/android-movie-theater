package woowacourse.movie.ui.home

import woowacourse.movie.model.Mapper.toUiModel
import woowacourse.movie.model.TheaterUiModel
import woowacourse.movie.model.main.MainModelHandler
import woowacourse.movie.theater.TheaterRepository

class HomePresenter(val view: HomeContract.View) : HomeContract.Presenter {

    override val theaters: List<TheaterUiModel>
        get() = TheaterRepository.getTheaters().map { it.toUiModel() }

    override fun initMainData() {
        val mainData = MainModelHandler.getMainData()

        view.initAdapter(mainData)
    }
}
