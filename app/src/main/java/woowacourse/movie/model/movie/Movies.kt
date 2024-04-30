package woowacourse.movie.model.movie

class Movies(val values: List<MovieContent>) {
    init {
        require(values.distinct().size == values.size) { ERROR_DUPLICATED_MOVIES }
    }

    companion object {
        private const val ERROR_DUPLICATED_MOVIES = "중복된 영화를 보유할 수 없습니다."
    }
}
