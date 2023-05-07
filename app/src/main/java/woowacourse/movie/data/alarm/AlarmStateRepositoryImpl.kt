package woowacourse.movie.data.alarm

import android.content.Context

class AlarmStateRepositoryImpl(
    context: Context,
) : AlarmStateRepository {

    private val alarmStateDataSource = AlarmStateDataSource(context)

    override fun getData(): Boolean {
        return alarmStateDataSource.getData()
    }

    override fun saveData(isAlarmOn: Boolean) {
        return alarmStateDataSource.saveData(isAlarmOn)
    }
}
