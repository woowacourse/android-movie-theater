package woowacourse.app.data.pushAlarm

interface PushAlarmDataSource {
    fun setPushAlarmOn()
    fun setPushAlarmOff()
    fun getPushAlarmState(): Boolean
}
