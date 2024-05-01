package woowacourse.movie.model.data

import woowacourse.movie.model.movie.MovieContent

object MovieContentsImpl : MovieContents {
    private const val EXCEPTION_INVALID_ID = "Movie not found with id: %d"
    private var id: Long = 0
    private val movieContents = mutableMapOf<Long, MovieContent>()

    init {
        save(HARRY_PORTER_WIZARD)
        save(HARRY_PORTER_SECRET)
    }

    override fun save(movieContent: MovieContent): Long {
        movieContents[id] = movieContent.copy(id = id)
        return id++
    }

    override fun find(id: Long): MovieContent {
        return movieContents[id] ?: throw NoSuchElementException(invalidIdMessage(id))
    }

    override fun findAll(): List<MovieContent> {
        return movieContents.map { it.value }
    }

    override fun deleteAll() {
        movieContents.clear()
    }

    private fun invalidIdMessage(id: Long) = EXCEPTION_INVALID_ID.format(id)
}
