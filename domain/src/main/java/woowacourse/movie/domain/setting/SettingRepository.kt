package woowacourse.movie.domain.setting

interface SettingRepository {
    fun getValue(key: String, default: Boolean = false): Boolean
    fun setValue(key: String, value: Boolean)
}
