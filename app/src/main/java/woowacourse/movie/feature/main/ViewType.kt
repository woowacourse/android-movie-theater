package woowacourse.movie.feature.main

enum class ViewType(val id: Int) {
    MOVIE(0),
    ADV(1);
    // TODO: ordinal로 변경 필요...

    companion object {
        fun of(id: Int): ViewType = values()[id]
    }
}
