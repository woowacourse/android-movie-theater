package woowacourse.movie.domain.reservation

import woowacourse.movie.domain.price.Price
import woowacourse.movie.domain.system.Seat
import java.time.LocalDateTime

data class Reservation(
    val title: String,
    val screeningDateTime: LocalDateTime,
    val seats: List<Seat>,
    val price: Price,
    val theaterName: String
)
