package woowacourse.movie.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalTime

@Parcelize
@JvmInline
value class MovieTime(
    val value: LocalTime = LocalTime.now(),
) : Parcelable {
    constructor(hour: Int, minute: Int) : this(LocalTime.of(hour, minute))
}
