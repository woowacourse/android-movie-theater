package woowacourse.movie.data.repository

import woowacourse.movie.domain.model.reservation.ReservationMovieInfo
import woowacourse.movie.domain.repository.ReservationMovieInfoRepository

object ReservationMovieInfoRepositoryImpl : ReservationMovieInfoRepository {
    private lateinit var reservationMovieInfo: ReservationMovieInfo

    override fun getScreeningMovieInfo(): ReservationMovieInfo {
        return reservationMovieInfo
    }

    override fun saveMovieInfo(reservationMovieInfo: ReservationMovieInfo) {
        this.reservationMovieInfo = reservationMovieInfo
    }
}
