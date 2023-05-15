package woowacourse.app.data.theater

import java.time.LocalTime

data class MovieTimeEntity(
    val id: Long,
    val theaterId: Long,
    val movieId: Long,
    val times: String,
) {
    companion object {
        fun convertToLocalTimes(times: String): List<LocalTime> {
            val localTimes = times.split(",")
            return localTimes.map {
                LocalTime.parse(it.trim())
            }
        }
    }
}
