package woowacourse.movie.domain.repository

import woowacourse.movie.domain.model.reservation.ReservationMovieInfo

interface ReservationMovieInfoRepository {
    fun getScreeningMovieInfo(): ReservationMovieInfo?

    fun saveMovieInfo(reservationMovieInfo: ReservationMovieInfo)
}
