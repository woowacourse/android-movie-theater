package woowacourse.movie.model.seat

data class Col(val value: Int) {
    init {
        require(value in MIN_COL_NUM..MAX_COL_NUM) { ERROR_INVALID_COL }
    }

    companion object {
        private const val MIN_COL_NUM = 0
        private const val MAX_COL_NUM = 3
        private const val ERROR_INVALID_COL = "지원하지 않는 좌석 열입니다"
    }
}
