package woowacourse.movie.domain.movie

import java.time.LocalTime

data class Schedule(val schedule: Map<String, List<LocalTime>>)
