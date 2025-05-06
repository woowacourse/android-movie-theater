package woowacourse.movie.domain.model

data class Advertisement(
    override val id: Long,
) : Content(id)
