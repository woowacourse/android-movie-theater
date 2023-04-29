package woowacourse.movie.movie.dto.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class MovieDateDto(val date: LocalDate) : Parcelable
