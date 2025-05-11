package woowacourse.movie.domain.model

data class SettingData(
    val key: String,
    val value: Boolean,
) {
    companion object {
        const val NOTIFICATION_KEY = "notification"
    }
}
