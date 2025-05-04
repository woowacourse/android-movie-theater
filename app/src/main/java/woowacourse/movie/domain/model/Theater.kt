package woowacourse.movie.domain.model

import woowacourse.movie.view.model.MovieUiModel
import woowacourse.movie.view.model.TheaterUIModel

class Theater(
    val name: String,
    val movies: List<Movie>,
)

fun Theater.toUiModel(
    movie: MovieUiModel,
    timeSlot: Int,
): TheaterUIModel =
    TheaterUIModel(
        name = this.name,
        movie = movie,
        timeSlotCount = timeSlot,
    )
