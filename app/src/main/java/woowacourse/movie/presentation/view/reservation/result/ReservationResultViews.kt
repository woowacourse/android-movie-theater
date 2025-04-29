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
        context
            .getString(R.string.reservation_datetime_format)
            .toDateTimeFormatter()
            ?.let { formatter ->
                binding.tvMovieDate.text = ticketBundle.dateTime.format(formatter)
            }
        binding.tvReservationCountInfo.text =
            context.getString(
                R.string.reservation_count_info,
                ticketBundle.size,
                ticketBundle.labels.joinToString { it.toLabel() },
            )
        binding.tvReservationTotalPrice.text =
            context.getString(R.string.reservation_total_price).format(ticketBundle.totalPrice)
        binding.tvCancelDescription.text =
            context.getString(
                R.string.reservation_result_cancel_time_description,
                cancellationTime,
            )
    }
}
