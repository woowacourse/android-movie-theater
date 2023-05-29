package woowacourse.movie.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalTime

@Parcelize
data class TheaterModel(
    val name: String,
    val size: Int,
    val times: Map<LocalDate, List<LocalTime>>,
) : Parcelable
