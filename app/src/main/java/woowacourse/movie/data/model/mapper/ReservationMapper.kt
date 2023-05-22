package woowacourse.movie.data.model.mapper

import domain.Reservation
import woowacourse.movie.data.model.uimodel.ReservationUIModel

object ReservationMapper : DomainViewMapper<Reservation, ReservationUIModel> {
    override fun toDomain(reservationUIModel: ReservationUIModel): Reservation {
        return Reservation(
            MovieMapper.toDomain(reservationUIModel.movie),
            TicketsMapper.toDomain(reservationUIModel.tickets)
        )
    }

    override fun toUI(reservation: Reservation): ReservationUIModel {
        return ReservationUIModel(
            MovieMapper.toUI(reservation.movie),
            TicketsMapper.toUI(reservation.detail)
        )
    }
}
