package woowacourse.movie.movie.movielist

import woowacourse.movie.dto.AdDto
import woowacourse.movie.movie.dto.movie.MovieDummy

class HomePresenter(private val view: HomeContract.View) : HomeContract.Presenter {
    override fun initFragment() {
        view.setUpMovieData(MovieDummy.movieDatas, AdDto.getAdData())
    }
}
