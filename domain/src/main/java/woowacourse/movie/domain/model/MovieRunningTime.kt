package woowacourse.movie.domain.model

import java.time.LocalTime

data class MovieRunningTime(val movie: Movie, val times: List<LocalTime>)
