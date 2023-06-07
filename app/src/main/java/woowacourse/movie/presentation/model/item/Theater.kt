package woowacourse.movie.presentation.model.item

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.presentation.model.MovieDate
import woowacourse.movie.presentation.model.MovieTime

typealias PresentationTheater = Theater

@Parcelize
data class Theater(
    val theaterName: String,
    val screenDates: List<MovieDate>,
    val screenTimes: List<MovieTime>,
) : ListItem, Parcelable {

    companion object {
        val EMPTY = Theater("", emptyList(), emptyList())

        private val dummyData = listOf(
            Theater(
                "선릉 극장",
                listOf(
                    MovieDate.now(),
                ),
                listOf(
                    MovieTime(11, 0),
                    MovieTime(17, 0),
                    MovieTime(19, 0),
                ),
            ),
            Theater(
                "잠실 극장",
                listOf(
                    MovieDate.now(),
                ),
                listOf(
                    MovieTime(11, 0),
                    MovieTime(12, 0),
                    MovieTime(13, 0),
                    MovieTime(14, 0),
                    MovieTime(16, 0),
                    MovieTime(17, 0),
                    MovieTime(19, 0),
                    MovieTime(20, 0),
                    MovieTime(21, 0),
                    MovieTime(22, 0),
                ),
            ),
            Theater(
                "강남 극장",
                listOf(
                    MovieDate.now(),
                ),
                listOf(
                    MovieTime(11, 0),
                    MovieTime(22, 0),
                ),
            ),
        )

        fun provideDummyData(): List<Theater> = dummyData
    }
}
