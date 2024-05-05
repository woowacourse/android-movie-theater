package woowacourse.movie.model

enum class SeatState {
    NONE,
    SELECTED,
    RESERVED,
    ;

    fun reserveState(): SeatState =
        when (this) {
            NONE -> SELECTED
            SELECTED -> RESERVED
            RESERVED -> RESERVED
        }
}
