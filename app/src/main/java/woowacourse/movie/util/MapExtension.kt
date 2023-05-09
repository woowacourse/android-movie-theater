package woowacourse.movie.util

fun <T, K> Map<T, List<K>>.getOrEmptyList(key: T): List<K> {
    return this[key] ?: emptyList<K>()
}
