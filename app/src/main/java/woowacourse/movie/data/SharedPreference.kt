package woowacourse.movie.data

interface SharedPreference {
    fun getBoolean(key: String, defValue: Boolean): Boolean
    fun setBoolean(key: String, boolean: Boolean)
}