package woowacourse.movie.feature.model

import android.icu.text.DecimalFormat
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.util.SeatLabelFormatter.formatLabel

@Parcelize
data class BookingInfoUiModel(
    val uid: Int = 0,
    val movie: MovieUiModel = MovieUiModel(),
    val theaterName: String = "",
    val date: MovieDateUiModel = MovieDateUiModel(),
    val movieTime: MovieTimeUiModel = MovieTimeUiModel(),
    val ticketCount: Int = 1,
    val totalPrice: Int = 0,
    val selectedSeats: Set<MovieSeatUiModel> = setOf<MovieSeatUiModel>(),
    val isRequiredSeatsSelected: Boolean = false,
) : Parcelable {
    fun getSelectedSeatsText(): String = selectedSeats.joinToString { formatLabel(it.row, it.column) }

    fun getTotalPriceText(): String = MONEY_DECIMAL_FORMAT.format(totalPrice)

    companion object {
        private val MONEY_DECIMAL_FORMAT = DecimalFormat("#,###")
    }
}
