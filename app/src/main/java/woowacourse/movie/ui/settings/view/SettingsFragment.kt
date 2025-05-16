package woowacourse.movie.ui.settings.view

import android.Manifest.permission
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import woowacourse.movie.MovieApplication
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingsBinding
import woowacourse.movie.ui.settings.contract.SettingsContract
import woowacourse.movie.ui.settings.presenter.SettingsPresenter

class SettingsFragment :
    Fragment(),
    SettingsContract.View {
    private val sharedPreference by lazy { (requireActivity().application as MovieApplication).sharedPreferences }
    private val settingsPresenter = SettingsPresenter(this)
    private var _binding: FragmentSettingsBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        settingsPresenter.loadSwitchChecked(isNotificationOptionChecked())
        settingsPresenter.refreshChecked()
        binding.notificationSwitchListener = notificationSwitchListener

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setSwitchChecked(isNotificationOptionChecked: Boolean) {
        if (isNotificationPermissionGranted().not()) {
            binding.switchSettingPostNotification.isChecked = false
        }
        binding.switchSettingPostNotification.isChecked = isNotificationOptionChecked
    }

    private fun isNotificationOptionChecked(): Boolean = sharedPreference.getBoolean(PREF_KEY_NOTIFICATION, false)

    private val notificationSwitchListener: NotificationSwitchListener =
        NotificationSwitchListener { isChecked ->
            if (isNotificationPermissionGranted().not()) {
                sharedPreference.edit { putBoolean(PREF_KEY_NOTIFICATION, false) }
                binding.switchSettingPostNotification.isChecked = false
                showPermissionToast()
            } else {
                sharedPreference.edit { putBoolean(PREF_KEY_NOTIFICATION, isChecked) }
                settingsPresenter.loadSwitchChecked(isNotificationOptionChecked())
            }
            settingsPresenter.refreshChecked()
        }

    private fun isNotificationPermissionGranted(): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission.POST_NOTIFICATIONS,
            ) == PERMISSION_GRANTED
        } else {
            true
        }

    private fun showPermissionToast() {
        Toast
            .makeText(
                requireContext(),
                getString(R.string.text_message_need_permission),
                Toast.LENGTH_SHORT,
            ).show()
    }

    companion object {
        private const val PREF_KEY_NOTIFICATION = "notification"
    }
}
