package woowacourse.movie.data.setting

import android.content.Context

interface AlarmSetting {
    var isEnable: Boolean

    companion object {
        const val MOVIE_REMINDER: Int = 0

        fun getInstance(context: Context, option: Int): AlarmSetting {
            return when (option) {
                MOVIE_REMINDER -> MovieStartReminderSetting.getInstance(context)
                else -> throw IllegalArgumentException()
            }
        }
    }
}
