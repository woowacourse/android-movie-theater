package woowacourse.movie.theater.view.contract

import woowacourse.movie.movielist.dto.MovieDto

interface TheaterContract {
    interface View {
        val presenter: Presenter

        fun initDetailData()
        fun setUpTheaterData(movie: MovieDto)
    }

    interface Presenter {
        var movie: MovieDto

        fun initData(data: MovieDto)
    }
}
