package woowacourse.movie.theater

object TheaterRepository {

    fun getTheater(theaterId: Long): Theater {
        return TheaterDatabase.selectTheater(theaterId).toTheater()
    }

    private fun TheaterEntity.toTheater(): Theater {
        return Theater(
            id = this.id,
            rowSize = this.rowSize,
            columnSize = this.columnSize,
        )
    }
}
