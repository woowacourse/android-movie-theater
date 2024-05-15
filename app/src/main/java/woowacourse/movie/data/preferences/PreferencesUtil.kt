package woowacourse.movie.data.preferences

interface PreferencesUtil {
    fun getBoolean(key: String): Boolean

    fun setBoolean(key: String, newValue: Boolean): Boolean
}
