package woowacourse.movie.repository

interface Repository<DOMAIN> {
    fun findAll(): List<DOMAIN>?
}
