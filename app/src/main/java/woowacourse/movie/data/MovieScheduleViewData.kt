package woowacourse.movie.data

import java.io.Serializable
import java.time.LocalTime

data class MovieScheduleViewData(val movie: MovieViewData, val times: List<LocalTime>) :
    Serializable
