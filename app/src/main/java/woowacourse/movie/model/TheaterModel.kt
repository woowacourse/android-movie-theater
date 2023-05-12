package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.Theater
import java.time.LocalTime

fun Theater.mapToTheaterModel(): TheaterModel {
    return TheaterModel(name, screeningTimes)
}

fun TheaterModel.mapToTheater(): Theater {
    return Theater(name, screeningTimes)
}

@Parcelize
data class TheaterModel(
    val name: String,
    val screeningTimes: List<LocalTime>
) : Parcelable
