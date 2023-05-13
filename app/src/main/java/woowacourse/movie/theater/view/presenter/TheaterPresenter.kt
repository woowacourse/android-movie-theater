package woowacourse.movie.theater.view.presenter

import woowacourse.movie.movielist.dto.MovieDto
import woowacourse.movie.theater.view.contract.TheaterContract

class TheaterPresenter(private val view: TheaterContract.View) : TheaterContract.Presenter {
    override lateinit var movie: MovieDto

    override fun initData(data: MovieDto) {
        movie = data
        view.setUpTheaterData(movie)
    }
}
