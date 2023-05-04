package woowacourse.movie.movie.utils

interface BasePreference {
    fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String): Boolean
}
