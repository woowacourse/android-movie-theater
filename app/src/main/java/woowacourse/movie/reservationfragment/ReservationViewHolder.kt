package woowacourse.movie.reservationfragment

import woowacourse.movie.R
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.domain.BookingStatus

class ReservationViewHolder(
    val binding: ItemReservationBinding,
    val reservations: List<BookingStatus>,
    val onClick: (BookingStatus) -> Unit,
) {
    private var position: Int = -1

    init {
        binding.itemReservation.setOnClickListener {
            binding.itemReservation.setBackgroundColor(
                binding.itemReservation.context
                .getColor(R.color.gray_200))
            onClick(reservations[position])
        }
    }

    fun bindReservation(position: Int) {
        this.position = position
        val reservation = reservations[position]
        binding.bookingStatus = reservation
    }
}
