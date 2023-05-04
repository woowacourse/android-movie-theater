package woowacourse.movie.domain.theater

import woowacourse.movie.domain.price.Price

data class SeatInfo(val size: Size, val gradeOfRow: Map<Int, Grade>) {
    init {
        gradeOfRow.keys.toList().forEach {
            require(size.row > it) { OUT_OF_RANGE_ROW }
        }
        require(size.row == gradeOfRow.keys.size) { NEED_EVERY_ROW_GRADE_INFO }
    }

    fun getPrice(row: Int): Price? = gradeOfRow[row]?.price

    fun isValidSeat(selectRow: Int, selectCol: Int): Boolean = selectRow < size.row && selectCol < size.col

    companion object {
        private const val OUT_OF_RANGE_ROW = "최대 row를 넘을 수 없습니다."
        private const val NEED_EVERY_ROW_GRADE_INFO = "모든 열에 대한 등급 정보가 있어야 합니다."
    }
}
