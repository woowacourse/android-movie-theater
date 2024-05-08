package woowacourse.movie.presentation.ui.main.setting

import woowacourse.movie.R
import woowacourse.movie.data.repository.local.PreferenceRepositoryImpl
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.presentation.base.BaseMvpBindingFragment
import woowacourse.movie.presentation.ui.main.setting.SettingContract.View

class SettingFragment : BaseMvpBindingFragment<FragmentSettingBinding>(), View {
    override val layoutResourceId: Int
        get() = R.layout.fragment_setting

    private val presenter by lazy { SettingPresenter(this, PreferenceRepositoryImpl()) }

    override fun initStartView() {
        binding.presenter = presenter
        presenter.loadNotificationMode()
    }

    override fun showNotificationMode(mode: Boolean) {
        requireActivity().runOnUiThread {
            binding.mode = mode
        }
    }

    companion object {
        const val TAG = "SETTING_FRAGMENT"
    }
}
