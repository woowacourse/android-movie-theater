package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.feature.common.itemModel.TicketsItemModel
import java.time.LocalDateTime

@Parcelize
data class TicketsState(
    val theater: TheaterState,
    val movie: MovieState,
    val dateTime: LocalDateTime,
    val totalDiscountedMoneyState: MoneyState,
    val tickets: List<TicketState>
) : Parcelable {
    fun convertToItemModel(onClick: (position: Int) -> Unit): TicketsItemModel {
        return TicketsItemModel(this, onClick)
    }
}
