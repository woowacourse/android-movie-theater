package woowacourse.movie.model

class Movies(val movies: List<Movie>) {
    fun insertAdvertisements(advertiseInterval: Int): List<ScreenView> =
        movies.chunked(advertiseInterval) { chunk ->
            if (chunk.size == advertiseInterval) {
                chunk + Advertisement()
            } else {
                chunk
            }
        }.flatten()
}
