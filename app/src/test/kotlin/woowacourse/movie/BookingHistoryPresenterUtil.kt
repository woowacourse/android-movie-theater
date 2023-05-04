package woowacourse.movie

import woowacourse.movie.model.ReservationUiModel
import java.time.LocalDateTime

fun ReservationUiModel(): ReservationUiModel {

    return ReservationUiModel(
        tickets = setOf(),
        paymentType = PaymentType.OFFLINE,
        payment = 0,
        movieId = 0,
        movieTitle = "제목없음",
        bookedDateTime = LocalDateTime.MIN,
        count = 1
    )
}