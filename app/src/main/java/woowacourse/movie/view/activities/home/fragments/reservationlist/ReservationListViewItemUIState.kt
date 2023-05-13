package woowacourse.movie.view.activities.home.fragments.reservationlist

import woowacourse.movie.domain.screening.Reservation
import java.time.LocalDateTime

data class ReservationListViewItemUIState(
    val screeningDateTime: LocalDateTime,
    val movieTitle: String,
    val reservationId: Long
) {
    companion object {
        fun from(reservation: Reservation): ReservationListViewItemUIState {
            val reservationId = reservation.id
                ?: throw IllegalArgumentException("예매의 아이디가 널이면 UI 상태를 생성할 수 없습니다.")

            return ReservationListViewItemUIState(
                reservation.screeningDateTime,
                reservation.movie.title,
                reservationId
            )
        }
    }
}
