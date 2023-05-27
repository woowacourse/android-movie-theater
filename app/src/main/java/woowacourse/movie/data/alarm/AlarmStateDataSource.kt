package woowacourse.movie.data.alarm

import android.content.Context
import androidx.core.content.edit
import woowacourse.movie.data.DataSource

class AlarmStateDataSource(
    context: Context,
) : DataSource<Boolean> {

    private val preferences = context.getSharedPreferences("movie", Context.MODE_PRIVATE)

    override fun getData(): Boolean {
        return preferences.getBoolean(KEY_SWITCH, false)
    }

    override fun saveData(dataSource: Boolean) {
        preferences.edit { putBoolean(KEY_SWITCH, dataSource) }
    }

    companion object {
        private const val KEY_SWITCH = "switch"
    }
}
