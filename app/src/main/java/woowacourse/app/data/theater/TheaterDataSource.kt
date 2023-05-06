package woowacourse.app.data.theater

interface TheaterDataSource {
    fun getTheaterEntities(): List<TheaterEntity>
    fun getTheaterEntity(theaterId: Long): TheaterEntity?
}
