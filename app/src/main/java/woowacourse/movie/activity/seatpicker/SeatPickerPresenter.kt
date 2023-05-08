package woowacourse.movie.activity.seatpicker

import woowacourse.movie.movie.MovieBookingInfo

class SeatPickerPresenter(
    private val view: SeatPickerContract.View,
    private val movieBookingInfo: MovieBookingInfo,
) : SeatPickerContract.Presenter
