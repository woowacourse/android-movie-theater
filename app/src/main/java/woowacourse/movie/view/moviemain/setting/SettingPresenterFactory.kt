package woowacourse.movie.view.moviemain.setting

import android.content.Context
import woowacourse.movie.AlarmPreference
import woowacourse.movie.data.MovieMockRepository
import woowacourse.movie.data.ReservationDatabase
import woowacourse.movie.data.SeatDatabase
import woowacourse.movie.data.dbhelper.ReservationDbHelper

class SettingPresenterFactory(val view: SettingContract.View, val context: Context) {
    fun createSettingPresenter(): SettingPresenter {
        return SettingPresenter(
            view,
            AlarmPreference.getInstance(context),
            ReservationDatabase(
                ReservationDbHelper(context).writableDatabase,
                SeatDatabase(context),
                MovieMockRepository
            )
        )
    }
}
