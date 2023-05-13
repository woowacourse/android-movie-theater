package woowacourse.movie.theater

import woowacourse.movie.dto.movie.MovieDto
import woowacourse.movie.theater.TheaterContract

class TheaterPresenter(private val view: TheaterContract.View) : TheaterContract.Presenter {
    override lateinit var movie: MovieDto

    override fun initFragment(data: MovieDto) {
        movie = data
        view.setUpTheaterData(movie)
    }
}
