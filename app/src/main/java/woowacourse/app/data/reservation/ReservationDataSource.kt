package woowacourse.app.data.reservation

interface ReservationDataSource {
    fun getReservationEntities(): List<ReservationEntity>
    fun getReservationEntity(id: Long): ReservationEntity?
    fun addReservationEntity(reservationEntity: ReservationEntity)
    fun getNewReservationId(): Long
}
