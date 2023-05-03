package woowacourse.movie

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import woowacourse.movie.ui.booking.DateTimeContract

fun ignoreDates(view: DateTimeContract.View) {
    every { view.setDates(any()) } just Runs
}

fun ignoreTimes(view: DateTimeContract.View) {
    every { view.setTimes(any()) } just Runs
}

fun ignoreListener(view: DateTimeContract.View) {
    every { view.initDateSelectedListener(any()) } just Runs
}
