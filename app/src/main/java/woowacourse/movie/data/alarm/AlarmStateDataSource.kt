package woowacourse.movie.data.alarm

import android.content.Context
import androidx.core.content.edit
import woowacourse.movie.data.DataSource
import woowacourse.movie.ui.setting.SettingFragment

class AlarmStateDataSource(
    context: Context,
) : DataSource<Boolean> {

    private val preferences = context.getSharedPreferences("movie", Context.MODE_PRIVATE)

    override fun getData(): Boolean {
        return preferences.getBoolean(SettingFragment.KEY_SWITCH, false)
    }

    override fun saveData(dataSource: Boolean) {
        preferences.edit { putBoolean(SettingFragment.KEY_SWITCH, dataSource) }
    }
}
