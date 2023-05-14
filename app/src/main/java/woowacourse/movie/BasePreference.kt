package woowacourse.movie

interface BasePreference {
    fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String): Boolean
}
