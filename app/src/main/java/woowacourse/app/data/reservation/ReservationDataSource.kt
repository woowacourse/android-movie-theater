package woowacourse.app.data.reservation

import java.time.LocalDateTime

interface ReservationDataSource {
    fun getReservationEntities(): List<ReservationEntity>
    fun getReservationEntity(id: Long): ReservationEntity?
    fun makeReservation(
        movieId: Long,
        bookedDateTime: LocalDateTime,
        paymentType: Int,
    ): ReservationEntity
}
