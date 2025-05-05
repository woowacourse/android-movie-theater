package woowacourse.movie.view.movies

enum class ViewType {
    ITEM_MOVIE,
    ITEM_AD,
    ;

    companion object {
        fun find(viewType: Int): ViewType {
            return ViewType.entries.find {
                it.ordinal == viewType
            } ?: throw IllegalArgumentException("잘못된 ViewType")
        }
    }
}
