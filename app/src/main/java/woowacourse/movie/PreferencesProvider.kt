package woowacourse.movie

interface PreferencesProvider {
    fun isAlarmEnabled(): Boolean

    fun setAlarmEnabled(enabled: Boolean)
}
