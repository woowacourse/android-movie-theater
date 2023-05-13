package woowacourse.movie.movielist.view.presenter

import woowacourse.movie.movielist.dto.AdDto
import woowacourse.movie.movielist.dto.MovieDummy
import woowacourse.movie.movielist.view.contract.HomeContract

class HomePresenter(private val view: HomeContract.View) : HomeContract.Presenter {
    override fun initFragment() {
        view.setUpMovieData(MovieDummy.movieDatas, AdDto.getAdData())
    }
}
