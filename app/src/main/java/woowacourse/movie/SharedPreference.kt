package woowacourse.movie

interface SharedPreference {
    fun getBoolean(key: String, defValue: Boolean): Boolean
    fun setBoolean(key: String, value: Boolean)
}
