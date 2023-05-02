package woowacourse.movie.movie

import androidx.annotation.DrawableRes
import com.woowacourse.domain.Movie
import woowacourse.movie.R
import java.io.Serializable
import java.time.LocalDate

fun MovieUIModel.toDomain() = Movie(poster, title, runningTime, synopsis, startDate, endDate)
fun Movie.toPresentation() = MovieUIModel(poster, title, runningTime, synopsis, startDate, endDate)

data class MovieUIModel(
    @DrawableRes val poster: Int,
    val title: String,
    val runningTime: Int,
    val synopsis: String,
    val startDate: LocalDate,
    val endDate: LocalDate
) : Serializable {
    companion object {
        val dummyData = MovieUIModel(
            R.drawable.no_image,
            "데이터를 불러올 수 없습니다.",
            0,
            "",
            LocalDate.of(2023, 7, 1),
            LocalDate.of(2023, 7, 1),
        )
    }
}
