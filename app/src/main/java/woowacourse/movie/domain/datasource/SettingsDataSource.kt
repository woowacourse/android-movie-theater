package woowacourse.movie.domain.datasource

interface SettingsDataSource {
    val isTicketAlarmChecked: Boolean

    fun setTicketAlarmChecked(isTicketAlarmChecked: Boolean)
}
