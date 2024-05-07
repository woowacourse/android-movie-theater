package woowacourse.movie.selectseat.uimodel

enum class SeatState {
    SELECTED,
    NONE, ;

    fun toggle(): SeatState =
        when (this) {
            SELECTED -> NONE
            NONE -> SELECTED
        }
}
