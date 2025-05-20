package woowacourse.movie.presentation.adapter

fun interface ClickListener<T> {
    fun onClick(item: T)
}
