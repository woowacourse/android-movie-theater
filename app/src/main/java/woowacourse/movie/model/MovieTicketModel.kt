package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.MovieTicket
import woowacourse.movie.model.seat.SeatModel
import woowacourse.movie.model.seat.mapToSeat
import woowacourse.movie.model.seat.mapToSeatModel

fun MovieTicketModel.mapToMovieTicket(): MovieTicket {
    return MovieTicket(
        theater,
        title,
        time.mapToTicketTime(),
        peopleCount.mapToPeopleCount(),
        seats.map { it.mapToSeat() }.toSet(),
        price.mapToPrice()
    )
}

fun MovieTicket.mapToMovieTicketModel(): MovieTicketModel {
    return MovieTicketModel(
        theater,
        title,
        time.mapToTicketTimeModel(),
        peopleCount.mapToPeopleCountModel(),
        seats.map { it.mapToSeatModel() }.toSet(),
        getDiscountPrice().mapToPriceModel()
    )
}

fun MovieTicket.mapToMovieTicketModelWithOriginalPrice(): MovieTicketModel {
    return MovieTicketModel(
        theater,
        title,
        time.mapToTicketTimeModel(),
        peopleCount.mapToPeopleCountModel(),
        seats.map { it.mapToSeatModel() }.toSet(),
        price.mapToPriceModel()
    )
}

@Parcelize
data class MovieTicketModel(
    val theater: String,
    val title: String,
    val time: TicketTimeModel,
    val peopleCount: PeopleCountModel,
    val seats: Set<SeatModel>,
    val price: PriceModel
) : Parcelable {
    fun isSelectedSeat(seat: SeatModel): Boolean = seats.contains(seat)
}
