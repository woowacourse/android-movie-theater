package woowacourse.movie.view.home

interface MovieClickListener {
    fun onReservationClick(movieId: Long)

    fun onAdvertisementClick(url: String)
}
