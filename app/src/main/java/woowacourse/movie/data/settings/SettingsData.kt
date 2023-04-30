package woowacourse.movie.data.settings

interface SettingsData {

    fun setBooleanData(key: String, value: Boolean)

    fun getBooleanData(key: String, defaultValue: Boolean): Boolean
}
