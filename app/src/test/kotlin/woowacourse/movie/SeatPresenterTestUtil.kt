package woowacourse.movie

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import woowacourse.movie.model.BookedMovie
import woowacourse.movie.ui.seat.SeatContract
import java.time.LocalDateTime

fun BookedMovie(ticketCount: Int) = BookedMovie(
    movieId = 1,
    theaterId = 0,
    ticketCount = ticketCount,
    bookedDateTime = LocalDateTime.of(2023, 4, 26, 10, 0)
)

fun ignorePaymentText(view: SeatContract.View) =
    every { view.setSeatPayment(any()) } just Runs

fun ignoreButtonState(view: SeatContract.View) =
    every { view.setButtonState(any()) } just Runs

fun ignoreOnSelected(view: SeatContract.View) =
    every { view.setSeatSelected(any(), any()) } just Runs
