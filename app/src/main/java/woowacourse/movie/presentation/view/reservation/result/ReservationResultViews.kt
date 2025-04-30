package woowacourse.movie.presentation.view.reservation.result

import android.content.Context
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationResultBinding
import woowacourse.movie.presentation.extension.toDateTimeFormatter
import woowacourse.movie.presentation.model.TicketBundleUiModel

class ReservationResultViews(
    private val context: Context,
    private val binding: FragmentReservationResultBinding,
) {
    fun bindReservationResult(
        ticketBundle: TicketBundleUiModel,
        cancellationTime: Int,
    ) {
        binding.tvMovieTitle.text = ticketBundle.title
        binding.tvMovieDate.text = ticketBundle.toDateTimeUiString()
        binding.tvReservationCountInfo.text = ticketBundle.toReservationCountUiString()
        binding.tvReservationTotalPrice.text = ticketBundle.toReservationTotalPriceUiString()
        binding.tvCancelDescription.text = setCancelDescription(cancellationTime)
    }

    private fun TicketBundleUiModel.toDateTimeUiString(): String {
        val formatter =
            context.getString(R.string.reservation_datetime_format).toDateTimeFormatter()
        return this.dateTime.format(formatter)
    }

    private fun TicketBundleUiModel.toReservationCountUiString(): String =
        context.getString(
            R.string.reservation_count_info,
            this.size,
            this.labels.joinToString { it.toLabel() },
            this.theaterName,
        )

    private fun TicketBundleUiModel.toReservationTotalPriceUiString(): String =
        context.getString(R.string.reservation_total_price).format(this.totalPrice)

    private fun setCancelDescription(cancellationTime: Int): String =
        context.getString(
            R.string.reservation_result_cancel_time_description,
            cancellationTime,
        )
}
