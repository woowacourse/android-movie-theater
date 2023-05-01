package woowacourse.movie.view.mapper

import domain.Reservation
import woowacourse.movie.view.mapper.MovieMapper.toDomain
import woowacourse.movie.view.mapper.MovieMapper.toUi
import woowacourse.movie.view.mapper.TicketsMapper.toDomain
import woowacourse.movie.view.mapper.TicketsMapper.toUi
import woowacourse.movie.view.model.ReservationUiModel

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
            detail.toUi()
        )
    }
}
