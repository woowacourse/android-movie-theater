package woowacourse.movie.ui.adapter.itemModel

interface ItemModel {
    val viewType: Int

    companion object {
        const val TYPE_AD = 0
        const val TYPE_MOVIE = 1
        const val TYPE_TICKETS = 3
        const val TYPE_CINEMA = 4
    }
}
