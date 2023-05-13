package woowacourse.movie.view.utils

import android.content.Context
import android.content.SharedPreferences
import woowacourse.movie.R

fun getPushAlarmReceptionIsWanted(context: Context): Boolean {
    val prefs: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    return prefs.getBoolean(context.getString(R.string.push_alarm_reception_is_wanted), false)
}

fun setPushAlarmReceptionIsWanted(context: Context, isWanted: Boolean) {
    val prefs: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    prefs.edit().putBoolean(context.getString(R.string.push_alarm_reception_is_wanted), isWanted)
        .apply()
}
