package woowacourse.movie.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
@JvmInline
value class MovieDate(
    val value: LocalDate = LocalDate.now(),
) : Parcelable {
    constructor(year: Int, month: Int, dayOfMonth: Int) : this(
        LocalDate.of(year, month, dayOfMonth),
    )
}
