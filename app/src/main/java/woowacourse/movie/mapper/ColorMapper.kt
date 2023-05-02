package woowacourse.movie.mapper

interface ColorMapper<T> {
    fun T.matchColor(): Int
}
