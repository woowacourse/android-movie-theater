package woowacourse.movie.domain.model.tools.cinema

class Cinema private constructor(
    private val schedule: Map<String, MovieTimes>,
) {

    fun findMovieTimes(cinemaName: String): MovieTimes? = schedule[cinemaName]

    companion object {
        fun of(vararg movieWithTimes: Pair<String, MovieTimes>): Cinema {
            return Cinema(movieWithTimes.toMap())
        }
    }
}
