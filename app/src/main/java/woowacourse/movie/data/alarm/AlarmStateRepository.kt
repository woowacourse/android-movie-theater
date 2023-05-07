package woowacourse.movie.data.alarm

interface AlarmStateRepository {
    fun getData(): Boolean
    fun saveData(isAlarmOn: Boolean)
}
