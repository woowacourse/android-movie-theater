package woowacourse.movie.util

interface Observer<T> {
    fun update(data: T)
}
