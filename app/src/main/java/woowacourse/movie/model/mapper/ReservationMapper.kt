package woowacourse.movie.model.mapper

import domain.Reservation
import woowacourse.movie.model.mapper.MovieMapper.toDomain
import woowacourse.movie.model.mapper.MovieMapper.toUi
import woowacourse.movie.model.mapper.TicketsMapper.toDomain
import woowacourse.movie.model.mapper.TicketsMapper.toUi
import woowacourse.movie.model.ReservationUiModel

object ReservationMapper : DomainViewMapper<Reservation, ReservationUiModel> {
    override fun ReservationUiModel.toDomain(): Reservation {
        return Reservation(
            movie.toDomain(),
            tickets.toDomain()
        )
    }

    override fun Reservation.toUi(): ReservationUiModel {
        return ReservationUiModel(
            movie.toUi(),
            tickets.toUi()
        )
    }
}
