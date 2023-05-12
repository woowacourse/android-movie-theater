package woowacourse.movie.fragment.home.contract.presenter

import woowacourse.movie.database.TheaterRepository
import woowacourse.movie.dto.movie.AdUIModel
import woowacourse.movie.dto.movie.MovieDummy
import woowacourse.movie.dto.movie.MovieUIModel
import woowacourse.movie.dto.movie.TheaterUIModel
import woowacourse.movie.fragment.home.contract.HomeContract

class HomePresenter(
    val view: HomeContract.View,
    private val repository: TheaterRepository,
) : HomeContract.Presenter {
    override fun loadDatas() {
        view.setRecyclerView(MovieDummy.movieDatas, AdUIModel.getAdData())
    }

    override fun onMovieItemClick(item: MovieUIModel) {
        val theaters = loadTheaters(item)
        view.showTheaterFragment(item, theaters)
    }

    private fun loadTheaters(item: MovieUIModel): List<TheaterUIModel> {
        return repository.getTheaterByMovieId(item.id)
    }

    override fun onAdItemClick(item: AdUIModel) {
        view.showAd(item)
    }
}
