package woowacourse.movie

import woowacourse.movie.ui.model.MovieTicketModel
import woowacourse.movie.ui.model.PeopleCountModel
import woowacourse.movie.ui.model.PriceModel
import woowacourse.movie.ui.model.TicketTimeModel
import woowacourse.movie.ui.model.seat.SeatModel
import java.time.LocalDateTime

fun createMovieTicketModel(
    title: String = "글로의 50가지 그림자",
    time: TicketTimeModel = TicketTimeModel(LocalDateTime.now()),
    peopleCount: PeopleCountModel = PeopleCountModel(1),
    seats: Set<SeatModel> = emptySet(),
    price: PriceModel = PriceModel(0),
    theaterName: String = "아현"
): MovieTicketModel {
    return MovieTicketModel(title, time, peopleCount, seats, price, theaterName)
}
