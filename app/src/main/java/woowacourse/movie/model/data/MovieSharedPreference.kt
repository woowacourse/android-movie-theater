package woowacourse.movie.model.data

interface MovieSharedPreference {
    fun setAlarmChecked(isChecked: Boolean)

    fun getAlarmChecked(): Boolean
}
