package woowacourse.movie.domain.theater

data class Size(val row: Int, val col: Int) {
    init {
        require(row > 0 && col > 0) { ROW_COL_OUT_OF_RANGE_MESSAGE }
    }

    companion object {
        private const val ROW_COL_OUT_OF_RANGE_MESSAGE = "row, col 수는 양수여야 합니다."
    }
}
