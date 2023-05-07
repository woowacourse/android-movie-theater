package woowacourse.movie.ui.home

import woowacourse.movie.model.TheaterUiModel
import woowacourse.movie.model.main.MainModelRepository

class HomePresenter(
    val view: HomeContract.View,
    val repository: MainModelRepository
) : HomeContract.Presenter {

    override val theaters: List<TheaterUiModel>
        get() = repository.getTheaters()

    override fun initMainData() {
        val mainData = repository.getMainData()

        view.initAdapter(mainData)
    }
}
