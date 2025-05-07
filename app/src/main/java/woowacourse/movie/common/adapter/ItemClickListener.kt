package woowacourse.movie.common.adapter

fun interface ItemClickListener<T> {
    fun onClick(item: T)
}
