package woowacourse.movie.model.mapper

import com.example.domain.model.Cinema
import woowacourse.movie.model.CinemaState

fun Cinema.asPresentation(): CinemaState =
    CinemaState(
        name,
        movies.map { it.asPresentation() }
    )
