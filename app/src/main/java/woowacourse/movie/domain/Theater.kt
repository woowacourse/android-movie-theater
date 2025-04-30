package woowacourse.movie.domain

import java.time.LocalTime

data class Theater(
    val name: String,
    val movies: Movies,
    val timeTable: Map<Title, List<LocalTime>>
) {
    fun isShowing(movie: Movie): Boolean {
        return movies.toList().contains(movie)
    }

    fun movieTimeTable(movie: Movie): List<LocalTime> {
        val title = Title(movie.title)
        val movieTimeTable = this.timeTable[title] ?: throw IllegalArgumentException()
        return movieTimeTable
    }
}
