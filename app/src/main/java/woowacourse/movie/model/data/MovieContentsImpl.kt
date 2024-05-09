package woowacourse.movie.model.data

// object MovieContentsImpl : DefaultMovieDataSource<Long, MovieContent> {
//    private const val EXCEPTION_INVALID_ID = "Movie not found with id: %d"
//    private var id: Long = 0
//    private val movieContents = mutableMapOf<Long, MovieContent>()
//
//    init {
//        save(HARRY_PORTER_WIZARD)
//        save(HARRY_PORTER_SECRET)
//        save(HARRY_PORTER_PRISONER)
//        save(HARRY_PORTER_FIRE_GLASS)
//    }
//
//    override fun save(data: MovieContent): Long {
//        movieContents[id] = data.copy(id = id)
//        return id++
//    }
//
//    override fun find(id: Long): MovieContent {
//        return movieContents[id] ?: throw NoSuchElementException(invalidIdMessage(id))
//    }
//
//    override fun findAll(): List<MovieContent> {
//        return movieContents.map { it.value }
//    }
//
//    override fun deleteAll() {
//        movieContents.clear()
//    }
//
//    private fun invalidIdMessage(id: Long) = EXCEPTION_INVALID_ID.format(id)
// }
