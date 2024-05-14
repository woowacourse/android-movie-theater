package woowacourse.movie.presentation.homefragments.setting

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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

        binding.switchSetting.setOnCheckedChangeListener { _, isChecked ->
            HomeActivity.sharedPreference.saveNotificationState(isChecked)
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            binding.isSwitchEnabled = ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
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
