package woowacourse.movie.movie

interface BasePreference {
    fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String): Boolean
}
