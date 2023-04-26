package woowacourse.movie

interface Setting {
    fun getSettingValue(key: String, default: Boolean = false): Boolean
    fun setSettingValue(key: String, value: Boolean)
}
