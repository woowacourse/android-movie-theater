package woowacourse.movie.domain.model

import woowacourse.movie.R
import woowacourse.movie.presentation.model.MovieDateUiModel
import woowacourse.movie.presentation.model.MovieUiModel
import woowacourse.movie.presentation.model.ReservationInfoUiModel
import woowacourse.movie.presentation.model.SeatUiModel
import woowacourse.movie.presentation.model.SeatsUiModel
import woowacourse.movie.presentation.model.TheaterUiModel
import woowacourse.movie.presentation.model.TheatersUiModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

val dummyMovie =
    MovieUiModel(
        "라라랜드",
        R.drawable.lalaland,
        MovieDateUiModel(
            LocalDate.of(2025, 5, 1),
            LocalDate.of(2025, 5, 30),
        ),
        120,
    )

val dummyTheater =
    TheaterUiModel("선릉", 20)

val dummyTheaters =
    TheatersUiModel(listOf(dummyTheater))

val dummySeats =
    SeatsUiModel(listOf(SeatUiModel("A1", 10000), SeatUiModel("C1", 15000)))

val dummyReservationInfo =
    ReservationInfoUiModel(
        "라라랜드",
        LocalDateTime.of(LocalDate.of(2025, 4, 1), LocalTime.of(14, 0)),
        dummySeats,
        2,
        "선릉",
    )
