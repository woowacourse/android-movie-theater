package woowacourse.movie.domain.model.tools.cinema

data class Cinema(
    val cinemaName: String,
    private val schedule: Map<Long, MovieTimes>,
) {

    fun findMovieTimes(movieId: Long): MovieTimes? = schedule[movieId]

    companion object {
        fun of(name: String, vararg movieWithTimes: Pair<Long, MovieTimes>): Cinema {
            return Cinema(name, movieWithTimes.toMap())
        }
    }
}
