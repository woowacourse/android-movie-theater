package woowacourse.movie.model

enum class SeatState {
    SELECTED,
    NONE, ;

    fun reserveState(): SeatState =
        when (this) {
            SELECTED -> NONE
            NONE -> SELECTED
        }
}
