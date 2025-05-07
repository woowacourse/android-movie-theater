package woowacourse.movie.fixture

import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.model.Movie
import woowacourse.movie.model.Schedule
import woowacourse.movie.ui.model.TheaterUiModel
import java.time.LocalTime

const val SEOLLEUNG = "선릉"

fun createTheater(
    name: String,
    movie: Movie,
): TheaterUiModel {
    return TheaterUiModel(
        place = name,
        schedule =
            Schedule(
                movie = movie,
                screeningTimes = listOf(LocalTime.of(11, 0), LocalTime.of(12, 0)),
            ).toUiModel(),
    )
}
