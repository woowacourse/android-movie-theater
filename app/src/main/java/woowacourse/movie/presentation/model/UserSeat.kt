package woowacourse.movie.presentation.model

import woowacourse.movie.domain.model.SeatModel
import java.io.Serializable

data class UserSeat(val seatModels: List<SeatModel>) : Serializable
