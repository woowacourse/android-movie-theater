package woowacourse.movie.theater

object TheaterRepository {

    fun getTheater(theaterId: Long): Theater {
        return TheaterDatabase.selectTheater(theaterId).toTheater()
    }

    fun getTheaters(): List<Theater> {
        return TheaterDatabase.theaters.map { it.toTheater() }
    }

    private fun TheaterEntity.toTheater(): Theater {
        return Theater(
            id = id,
            name = name,
            rowSize = rowSize,
            columnSize = columnSize,
            screeningTimes = screeningTimes
        )
    }
}
