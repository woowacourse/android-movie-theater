package woowacourse.movie.feature.bookinghistory.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.databinding.ItemBookingHistoryBinding
import woowacourse.movie.feature.model.BookingInfoUiModel

class BookingHistoryAdapter(
    private val handler: BookingHistoryViewHolder.Handler,
) : ListAdapter<BookingInfoUiModel, BookingHistoryViewHolder>(BookingHistoryDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BookingHistoryViewHolder {
        val binding = ItemBookingHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookingHistoryViewHolder(binding, handler)
    }

    override fun onBindViewHolder(
        holder: BookingHistoryViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }
}
