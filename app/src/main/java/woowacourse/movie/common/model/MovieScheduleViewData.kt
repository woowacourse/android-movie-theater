package woowacourse.movie.common.model

import java.io.Serializable
import java.time.LocalTime

data class MovieScheduleViewData(val movie: MovieViewData, val times: List<LocalTime>) :
    Serializable {
    companion object {
        const val MOVIE_SCHEDULE_EXTRA_NAME = "movieSchedule"
    }
}
