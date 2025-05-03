package woowacourse.movie.view.base

interface BaseViewHolderItem {
    val name: String
    val viewType: Int

    override operator fun equals(other: Any?): Boolean
}
