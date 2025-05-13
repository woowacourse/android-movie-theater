package woowacourse.movie.main.sharedPreference

interface PreferencesProvider {
    fun isAlarmEnabled(): Boolean

    fun setAlarmEnabled(enabled: Boolean)
}
