package woowacourse.movie.model.seat

data class Row(val value: Int) {
    init {
        require(value in MIN_ROW_NUM..MAX_ROW_NUM) { ERROR_INVALID_ROW }
    }

    companion object {
        private const val MIN_ROW_NUM = 0
        private const val MAX_ROW_NUM = 4
        private const val ERROR_INVALID_ROW = "지원하지 않는 좌석 행입니다"
    }
}
