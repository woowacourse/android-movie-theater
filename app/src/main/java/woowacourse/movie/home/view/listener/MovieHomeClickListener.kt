package woowacourse.movie.home.view.listener

import woowacourse.movie.model.Advertisement

interface MovieHomeClickListener {
    fun onReservationButtonClick(movieId: Long)

    fun onAdvertisementClick(advertisement: Advertisement)
}
