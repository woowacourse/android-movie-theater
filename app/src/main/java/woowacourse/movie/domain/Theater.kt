package woowacourse.movie.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalTime

@Parcelize
data class Theater(
    val name: String,
    val movies: Movies,
    val timeTable: Map<Title, List<LocalTime>>,
) : Parcelable {
    fun isShowing(movie: Movie): Boolean {
        return movies.toList().contains(movie)
    }

    fun movieTimeTable(movie: Movie): List<LocalTime> {
        val title = Title(movie.title)
        val movieTimeTable = this.timeTable[title] ?: throw IllegalArgumentException()
        return movieTimeTable
    }
}
