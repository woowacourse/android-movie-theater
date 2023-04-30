package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.feature.reservationList.itemModel.TicketsItemModel
import java.time.LocalDateTime

@Parcelize
data class TicketsState(
    val movieState: MovieState,
    val dateTime: LocalDateTime,
    val totalDiscountedMoneyState: MoneyState,
    val tickets: List<TicketState>
) : Parcelable {
    fun convertToItemModel(onClick: (position: Int) -> Unit): TicketsItemModel {
        return TicketsItemModel(this, onClick)
    }
}
