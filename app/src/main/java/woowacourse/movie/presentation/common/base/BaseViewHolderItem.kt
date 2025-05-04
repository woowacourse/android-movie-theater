package woowacourse.movie.presentation.common.base

interface BaseViewHolderItem {
    val id: Long
    val viewType: Int

    override operator fun equals(other: Any?): Boolean
}
