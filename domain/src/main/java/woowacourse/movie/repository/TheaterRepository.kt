package woowacourse.movie.repository

import woowacourse.movie.domain.theater.Theater

object TheaterRepository {

    private var next_id: Long = 1L
    private val theaters: MutableMap<Long, Theater> = mutableMapOf()

    init {
        initSampleData()
    }

    private fun initSampleData() {
        save(createJamsilTheater())
        save(createSeolleungTheater())
        save(createGangnamTheater())
        save(createLongNameTheater())
    }

    private fun createJamsilTheater(): Theater {
        return Theater("잠실", 5, 4)
    }

    private fun createSeolleungTheater(): Theater {
        return Theater("선릉", 5, 4)
    }

    private fun createGangnamTheater(): Theater {
        return Theater("강남", 5, 4)
    }

    private fun createLongNameTheater(): Theater {
        return Theater("너무너무너무너무너무 이름이 긴 극장", 5, 4)
    }

    fun save(theater: Theater) {
        if (theater.id == null) theater.id = next_id++
        theaters[theater.id!!] = theater
    }

    fun findById(id: Long): Theater? {
        return theaters[id]
    }

    fun findAll(): List<Theater> {
        return theaters.values.toList()
    }
}
