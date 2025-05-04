package woowacourse.movie.domain.model

import woowacourse.movie.R
import woowacourse.movie.view.model.MovieTicket
import woowacourse.movie.view.model.MovieUiModel
import woowacourse.movie.view.model.ReservationInfo
import woowacourse.movie.view.model.TheaterUIModel
import java.time.LocalDate

val dummyMovie =
    MovieUiModel(
        "라라랜드",
        R.drawable.lalaland,
        "2025.04.01",
        "2025.04.30",
        120,
    )

val dummyTicket =
    MovieTicket(
        "라라랜드",
        LocalDate.of(2025, 4, 1),
        "14:00",
        3,
        "선릉",
    )

val dummyReservationInfo =
    ReservationInfo(
        "라라랜드",
        LocalDate.of(2025, 4, 1),
        "14:00",
        Seats.create(),
        20000,
        "선릉",
    )

val dummyTheaterUIModel =
    TheaterUIModel("선릉", dummyMovie, 1)
