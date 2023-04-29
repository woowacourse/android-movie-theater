package woowacourse.movie.movie.dto.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalTime

@Parcelize
data class MovieTimeDto(val time: LocalTime) : Parcelable
