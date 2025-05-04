package woowacourse.movie.domain.model.cinema.screen

data class ScreenSize(
    val rowSize: Int,
    val colSize: Int,
) {
    init {
        require(rowSize > MINIMUM_SIZE && colSize > MINIMUM_SIZE) {
            INVALID_SCREEN_SIZE.format(
                rowSize,
                colSize,
            )
        }
    }

    companion object {
        private const val MINIMUM_SIZE = 0
        private const val INVALID_SCREEN_SIZE = "스크린 크기는 ${MINIMUM_SIZE}보다 커야 합니다. rowSize: %d, colSize: %d"
    }
}
