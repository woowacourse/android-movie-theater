package woowacourse.movie.provider

import android.content.Context
import androidx.fragment.app.Fragment
import woowacourse.movie.MovieTheaterApplication
import woowacourse.movie.view.setting.SettingContract
import woowacourse.movie.view.setting.SettingPresenter

class SettingProvider(context: Context) {
    private val application = context.applicationContext as MovieTheaterApplication

    fun <T> settingPresenter(view: T) where T : SettingContract.View, T : Fragment =
        SettingPresenter(
            view,
            application.repositoryProvider.ticketRepository,
            application.repositoryProvider.settingRepository,
        )
}
