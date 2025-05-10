package woowacourse.movie.feature.bookinghistory.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.R
import woowacourse.movie.feature.model.BookingInfoUiModel

class BookingHistoryAdapter(
    private val onBookingHistoryClick: (BookingInfoUiModel) -> Unit,
) : ListAdapter<BookingInfoUiModel, BookingHistoryViewHolder>(
        BookingHistoryDiffCallback,
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BookingHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BookingHistoryViewHolder(
            inflater.inflate(
                R.layout.item_booking_history,
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(
        holder: BookingHistoryViewHolder,
        position: Int,
    ) {
        val item: BookingInfoUiModel = getItem(position)
        holder.bind(onBookingHistoryClick, item)
    }
}
