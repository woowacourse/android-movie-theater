package woowacourse.movie.model

import com.woowacourse.domain.ScreeningSchedule
import java.io.Serializable
import java.time.LocalTime

fun ScreeningScheduleUIModel.toDomain() = ScreeningSchedule(movie.toDomain(), screeningTime)
fun ScreeningSchedule.toPresentation() =
    ScreeningScheduleUIModel(movie.toPresentation(), screeningTime)

class ScreeningScheduleUIModel(
    val movie: MovieUIModel,
    val screeningTime: List<LocalTime>,
) : Serializable
