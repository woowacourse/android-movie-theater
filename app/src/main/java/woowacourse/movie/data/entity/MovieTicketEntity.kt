package woowacourse.movie.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class MovieTicketEntity(
    @Embedded val reservationInfoEntity: ReservationInfoEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "reservationId",
    )
    val seats: List<SeatEntity>,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "id",
    )
    val movie: MovieEntity,
    @Relation(
        parentColumn = "theaterId",
        entityColumn = "id",
    )
    val theater: TheaterEntity,
)
