package woowacourse.movie.ui.main.setting

import android.os.Bundle
import android.view.View
import woowacourse.movie.DefaultPreference
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.ui.Toaster
import woowacourse.movie.ui.base.BaseFragment
import woowacourse.movie.ui.main.MainActivity.Companion.PERMISSIONS
import woowacourse.movie.util.hasPermissions

class SettingFragment : BaseFragment(), SettingContract.View {
    override lateinit var presenter: SettingContract.Presenter
    override lateinit var binding: FragmentSettingBinding

    override fun initBinding() {
        binding = FragmentSettingBinding.inflate(layoutInflater)
    }

    override fun initPresenter() {
        presenter = SettingPresenter(this, DefaultPreference(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setUpSwitch(NOTIFICATIONS, false)

        binding.notificationSwitch.setOnCheckedChangeListener { switchCompat, _ ->
            val permission = requireContext().hasPermissions(PERMISSIONS)
            val isChecked = when {
                permission -> switchCompat.isChecked
                else -> {
                    Toaster.showToast(requireContext(), "알림 권한을 허용해주세요.")
                    false
                }
            }
            presenter.updateSwitch(NOTIFICATIONS, isChecked)
        }
    }

    override fun setSwitchChecked(boolean: Boolean) {
        binding.notificationSwitch.isChecked = boolean
    }

    companion object {
        const val NOTIFICATIONS = "notifications"
    }
}
