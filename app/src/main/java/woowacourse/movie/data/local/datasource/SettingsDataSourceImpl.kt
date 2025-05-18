package woowacourse.movie.data.local.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import woowacourse.movie.domain.datasource.SettingsDataSource

class SettingsDataSourceImpl private constructor(private val settingPreferences: SharedPreferences) :
    SettingsDataSource {
        override val isTicketAlarmChecked
            get() =
                settingPreferences.getBoolean(
                    IS_TICKET_ALARM_KEY,
                    false,
                )

        override fun setTicketAlarmChecked(isTicketAlarmChecked: Boolean) {
            settingPreferences.edit {
                putBoolean(IS_TICKET_ALARM_KEY, isTicketAlarmChecked)
            }
        }

        companion object {
            private const val IS_TICKET_ALARM_KEY = "isTicketAlarm"
            private const val SETTINGS_KEY = "settings"

            fun of(context: Context) = SettingsDataSourceImpl(context.getSharedPreferences(SETTINGS_KEY, Context.MODE_PRIVATE))
        }
    }
