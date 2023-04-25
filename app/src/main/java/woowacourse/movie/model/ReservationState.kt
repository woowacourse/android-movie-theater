package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.ui.main.itemModel.ReservationItemModel
import java.time.LocalDateTime

@Parcelize
data class ReservationState(
    val movieState: MovieState,
    val dateTime: LocalDateTime,
    val countState: CountState
) : Parcelable {
    fun toItemModel(onClick: (position: Int) -> Unit): ReservationItemModel {
        return ReservationItemModel(this, onClick)
    }
}
