package woowacourse.movie.common.mapper

interface ColorMapper<T> {
    fun T.matchColor(): Int
}
