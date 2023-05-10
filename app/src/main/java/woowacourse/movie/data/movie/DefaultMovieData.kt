package woowacourse.movie.data.movie

import woowacourse.movie.R
import woowacourse.movie.presentation.model.MovieModel
import java.time.LocalDate

object DefaultMovieData {
    val defaultMovie = MovieModel(
        0L,
        title = "영화를 불러올 수 없습니다.",
        screeningStartDate = LocalDate.MAX,
        screeningEndDate = LocalDate.MAX,
        999,
        "영화를 불러올 수 없습니다. 다시 시작해 주세요.",
        thumbnail = R.drawable.default_thumbnail,
        poster = R.drawable.default_poster,
    )
}
