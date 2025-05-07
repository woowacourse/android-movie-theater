package woowacourse.movie.presentation.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
class TheatersUiModel(
    val theaters: Map<String, List<LocalDateTime>>,
) : Parcelable

fun TheatersUiModel.toTheaterUiModels() = this.theaters.map { TheaterUiModel(it.key, it.value) }
