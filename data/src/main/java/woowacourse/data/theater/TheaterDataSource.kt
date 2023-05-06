package woowacourse.data.theater

interface TheaterDataSource {
    fun getTheaterEntities(): List<TheaterEntity>
    fun getTheaterEntity(theaterId: Long): TheaterEntity?
}
