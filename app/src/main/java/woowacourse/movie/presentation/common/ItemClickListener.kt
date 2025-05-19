package woowacourse.movie.presentation.common

fun interface ItemClickListener<T> {
    fun onClick(item: T)
}
