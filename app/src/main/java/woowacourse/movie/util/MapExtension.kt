package woowacourse.movie.util

fun <T, K> Map<T, List<K>>.getOrEmptyList(key: T): List<K> {
    return this[key] ?: emptyList<K>()
}

fun <T, K> Map<T, K>.getKeyFromIndex(index: Int): T {
    return keys.toList()[index]
}
