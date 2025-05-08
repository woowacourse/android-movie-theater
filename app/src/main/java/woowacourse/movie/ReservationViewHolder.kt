package woowacourse.movie

import android.view.View
import android.widget.TextView
import woowacourse.movie.domain.BookingStatus


class ReservationViewHolder(
    val view: View,
    val reservations: List<BookingStatus>,
) {
    private val title: TextView = view.findViewById(R.id.tv_reservation_movie_title)
    private val reservationDate: TextView = view.findViewById(R.id.tv_reservation_date)
    private val reservationTime: TextView = view.findViewById(R.id.tv_reservation_time)
    private val theater: TextView = view.findViewById(R.id.tv_reservation_theater)

    init {
        val view: View = view.findViewById(R.id.item_reservation)
        view.setOnClickListener {
            view.setBackgroundColor(view.context.getColor(R.color.gray_200))
            // 이동 함수 생성
        }
    }

    fun bindReservation(position: Int) {
        val reservation = reservations[position]
        title.text = reservation.movie.title
        reservationDate.text = reservationDate.context.getString(
            R.string.reservation_date,
            reservation.bookedTime.year,
            reservation.bookedTime.monthValue,
            reservation.bookedTime.dayOfMonth
        )
        reservationTime.text = reservationTime.context.getString(
            R.string.reservation_running_Time,
            reservation.bookedTime.hour,
            reservation.bookedTime.minute,
        )
        theater.text = theater.context.getString(
            R.string.reservation_theater,
            reservation.theater.name
        )
    }
}
