package woowacourse.movie.ui.main.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.DefaultPreference
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.ui.Toaster
import woowacourse.movie.ui.main.MainActivity.Companion.PERMISSIONS
import woowacourse.movie.util.hasPermissions

class SettingFragment : Fragment(R.layout.fragment_setting), SettingContract.View {
    private lateinit var presenter: SettingContract.Presenter
    private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpBinding()
        setUpPresenter()
        return binding.root
    }

    private fun setUpBinding() {
        binding = FragmentSettingBinding.inflate(layoutInflater)
    }

    private fun setUpPresenter() {
        presenter = SettingPresenter(this, DefaultPreference(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getBoolean(NOTIFICATIONS, false)

        binding.notificationSwitch.setOnCheckedChangeListener { switchCompat, _ ->
            val permission = requireContext().hasPermissions(PERMISSIONS)
            val isChecked = when {
                permission -> switchCompat.isChecked
                else -> {
                    Toaster.showToast(requireContext(), "알림 권한을 허용해주세요.")
                    false
                }
            }
            presenter.setBoolean(NOTIFICATIONS, isChecked)
        }
    }

    override fun setSwitchChecked(boolean: Boolean) {
        binding.notificationSwitch.isChecked = boolean
    }

    companion object {
        const val NOTIFICATIONS = "notifications"
    }
}
