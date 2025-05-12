package woowacourse.movie.provider

import androidx.fragment.app.Fragment
import woowacourse.movie.provider.RepositoryProvider.settingRepository
import woowacourse.movie.provider.RepositoryProvider.ticketRepository
import woowacourse.movie.view.setting.SettingContract
import woowacourse.movie.view.setting.SettingPresenter

object SettingProvider {
    fun <T> settingPresenter(view: T) where T : SettingContract.View, T : Fragment =
        SettingPresenter(
            view,
            ticketRepository(view.requireContext().applicationContext),
            settingRepository(view.requireContext().applicationContext),
        )
}
