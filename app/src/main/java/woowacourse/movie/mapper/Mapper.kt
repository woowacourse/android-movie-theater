package woowacourse.movie.mapper

interface Mapper<T, R> {
    fun T.toView(): R
    fun R.toDomain(): T
}
