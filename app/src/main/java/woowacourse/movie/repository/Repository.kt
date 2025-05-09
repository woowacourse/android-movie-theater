package woowacourse.movie.repository

interface Repository<DOMAIN> {
    fun findAll(): Result<List<DOMAIN>>

    fun save(value: DOMAIN): Result<Unit>
}
