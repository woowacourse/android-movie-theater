package woowacourse.movie.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalTime

@Parcelize
data class TheaterModel(
    val name: String,
    val times: List<LocalTime>
) : Parcelable
