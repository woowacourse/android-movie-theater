package woowacourse.movie.common.adapter

fun interface ClickListener<T> {
    fun onClick(item: T)
}
