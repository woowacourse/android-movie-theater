package woowacourse.movie.view.moviemain

import woowacourse.movie.AlarmPreference

class MovieMainPresenter(
    private val alarmPreference: AlarmPreference
) : MovieMainContract.Presenter {
    override fun setAlarmPreference(isAlarmOn: Boolean) {
        alarmPreference.setIsAlarmOn(isAlarmOn)
    }
}
