package woowacourse.movie.presentation.ui.main.home.adapter

enum class ScreenViewType(val value: Int) {
    Screen(0),
    Ad(1),
    ;

    companion object {
        fun of(value: Int): ScreenViewType =
            when (value) {
                0 -> Screen
                1 -> Ad
                else -> throw IllegalArgumentException("잘못된 viewType입니다.")
            }
    }
}
