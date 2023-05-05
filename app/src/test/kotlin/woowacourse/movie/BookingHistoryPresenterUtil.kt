package woowacourse.movie

import woowacourse.movie.model.ReservationUiModel
import java.time.LocalDateTime

fun ReservationUiModel(theaterId: Long = 0, movieId: Long= 0): ReservationUiModel {

    return ReservationUiModel(
        theaterId = theaterId,
        tickets = setOf(),
        paymentType = PaymentType.OFFLINE,
        payment = 0,
        movieId = movieId,
        movieTitle = "제목없음",
        bookedDateTime = LocalDateTime.MIN,
        count = 1
    )
}