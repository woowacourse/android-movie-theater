package woowacourse.movie.presentation.view

import android.content.Context
import android.content.Intent
import woowacourse.movie.R
import woowacourse.movie.presentation.view.reservation.detail.MovieDetailActivity
import woowacourse.movie.presentation.view.reservation.result.ReservationResultActivity
import woowacourse.movie.presentation.view.reservation.seat.SeatSelectionActivity

val posterImageId = R.drawable.harrypotter_poster
const val TITLE = "title"
const val SCREENING_DATE = "2024.04.18"
const val RUNNING_TIME = 100
const val SUMMARY = "summary"
const val RESERVATION_COUNT = 1
const val TOTAL_PRICE = 26000

fun detailActivityIntent(context: Context): Intent =
    Intent(context, MovieDetailActivity::class.java).apply {
        putExtra("posterImageId", posterImageId)
        putExtra("title", TITLE)
        putExtra("screeningDate", SCREENING_DATE)
        putExtra("runningTime", RUNNING_TIME)
        putExtra("summary", SUMMARY)
    }

fun seatSelectionActivityIntent(context: Context): Intent =
    Intent(context, SeatSelectionActivity::class.java).apply {
        putExtra(MovieDetailActivity.TITLE_KEY, TITLE)
        putExtra(MovieDetailActivity.RESERVATION_COUNT_KEY, RESERVATION_COUNT)
    }

fun reservationResultActivityIntent(context: Context): Intent =
    Intent(context, ReservationResultActivity::class.java).apply {
        putExtra("title", TITLE)
        putExtra("screeningDate", SCREENING_DATE)
        putExtra("reservationCount", RESERVATION_COUNT)
        putExtra("totalPrice", TOTAL_PRICE)
    }
