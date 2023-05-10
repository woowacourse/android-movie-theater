package woowacourse.movie.ui.bookinghistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.BookingHistoryItemBinding
import woowacourse.movie.model.ReservationUiModel
import woowacourse.movie.util.formatBookedDateTime

class BookingHistoryViewHolder private constructor(
    private val binding: BookingHistoryItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var reservation: ReservationUiModel

    fun bind(reservation: ReservationUiModel) {
        this.reservation = reservation
        binding.textItemBookedDateTime.text = reservation.bookedDateTime.formatBookedDateTime()
        binding.textItemMovieName.text = reservation.movieTitle
    }

    fun setOnReservationClickListener(
        onClicked: (reservation: ReservationUiModel) -> Unit
    ) {
        binding.root.setOnClickListener { onClicked(reservation) }
    }

    companion object {

        fun from(parent: ViewGroup): BookingHistoryViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = BookingHistoryItemBinding.inflate(layoutInflater, parent, false)

            return BookingHistoryViewHolder(binding)
        }
    }
}
