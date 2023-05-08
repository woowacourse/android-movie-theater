package domain

class Movies(val value: List<Movie>) {
    fun find(movieTitle: String): Movie {
        return value.find {
            it.title == movieTitle
        } ?: throw IllegalStateException("해당 영화를 찾을 수 없습니다.")
    }
}
