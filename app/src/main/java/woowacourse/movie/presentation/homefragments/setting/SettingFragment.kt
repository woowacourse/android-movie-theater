package woowacourse.movie.presentation.homefragments.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.presentation.home.HomeActivity

class SettingFragment : Fragment(), SettingContract.View {
    private var _binding: FragmentSettingBinding? = null
    val binding get() = _binding!!
    private val presenter by lazy { SettingPresenter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadNotificationState(HomeActivity.sharedPreference.isPushNotificationActivated())

        binding.isSwitchEnabled = arguments?.getBoolean(KEY_ENABLED, true)

        binding.switchSetting.setOnCheckedChangeListener { _, isChecked ->
            HomeActivity.sharedPreference.saveNotificationState(isChecked)
        }
    }

    override fun setNotificationState(isChecked: Boolean) {
        binding.switchSetting.isChecked = isChecked
    }

    companion object {
        private const val KEY_ENABLED = "isEnabled"

        fun createBundle(isEnabled: Boolean): Bundle {
            return Bundle().apply { putBoolean(KEY_ENABLED, isEnabled) }
        }
    }
}
