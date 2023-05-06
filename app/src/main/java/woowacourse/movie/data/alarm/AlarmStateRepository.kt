package woowacourse.movie.data.alarm

import android.content.Context
import woowacourse.movie.data.DataSource

class AlarmStateRepository(
    context: Context,
) : DataSource<Boolean> {

    private val alarmStateDataSource = AlarmStateDataSource(context)

    override fun getData(): Boolean {
        return alarmStateDataSource.getData()
    }

    override fun saveData(dataSource: Boolean) {
        return alarmStateDataSource.saveData(dataSource)
    }
}
