package woowacourse.movie.repository

import woowacourse.movie.data.alarm.AlarmStateRepository

object FakeAlarmStateRepository : AlarmStateRepository {
    private var isAlarmOn: Boolean = false

    override fun getData(): Boolean {
        return isAlarmOn
    }

    override fun saveData(dataSource: Boolean) {
        isAlarmOn = dataSource
    }
}
