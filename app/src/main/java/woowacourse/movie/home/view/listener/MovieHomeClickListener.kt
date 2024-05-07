package woowacourse.movie.home.view.listener

import woowacourse.movie.home.view.adapter.movie.HomeContent.Advertisement

interface MovieHomeClickListener {
    fun onReservationButtonClick(movieId: Long)

    fun onAdvertisementClick(advertisement: Advertisement)
}
