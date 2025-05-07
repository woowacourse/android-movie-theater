package woowacourse.movie.presentation.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.model.cinema.MovieSchedule
import woowacourse.movie.domain.model.cinema.Theater
import java.time.LocalDateTime

@Parcelize
data class TheaterUiModel(
    val name: String,
    val times: List<LocalDateTime>,
) : Parcelable

fun TheaterUiModel.toDomain(movieId: Int): Theater = Theater(name, listOf(MovieSchedule(movieId, times)))
