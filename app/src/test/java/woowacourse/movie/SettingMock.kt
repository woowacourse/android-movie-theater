package woowacourse.movie.view

import woowacourse.movie.system.Setting

class SettingMock : Setting {
    private val settings: MutableMap<String, Boolean> = mutableMapOf()
    override fun getValue(key: String, default: Boolean): Boolean {
        return settings[key] ?: default
    }

    override fun setValue(key: String, value: Boolean) {
        settings[key] = value
    }
}
