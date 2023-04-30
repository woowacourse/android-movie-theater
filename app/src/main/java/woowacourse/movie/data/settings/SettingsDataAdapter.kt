package woowacourse.movie.data.settings

class SettingsDataAdapter(private val settingsData: SettingsData) : SettingsData {

    override fun setBooleanData(key: String, value: Boolean) =
        settingsData.setBooleanData(key, value)

    override fun getBooleanData(key: String, defaultValue: Boolean) =
        settingsData.getBooleanData(key, defaultValue)
}
