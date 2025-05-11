package woowacourse.movie.ui.settings.view

import android.Manifest.permission
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
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
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingsBinding
import woowacourse.movie.ui.settings.contract.SettingsContract
import woowacourse.movie.ui.settings.presenter.SettingsPresenter

class SettingsFragment :
    Fragment(),
    SettingsContract.View {
    private val settingsPresenter = SettingsPresenter(this)
    private var _binding: FragmentSettingsBinding? = null
    val binding get() = _binding!!

    private val sharedPreference: SharedPreferences by lazy {
        requireContext().getSharedPreferences(PREF_NAME, MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        settingsPresenter.loadChecked(isSettingChecked())
        settingsPresenter.refreshChecked()
        binding.notificationSwitchListener = notificationSwitchListener

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setSwitchChecked() {
        if (isNotificationPermissionGranted() == false) {
            binding.switchSettingPostNotification.isChecked = false
        }
        binding.switchSettingPostNotification.isChecked = isSettingChecked()
    }

    private fun isSettingChecked(): Boolean = sharedPreference.getBoolean(PREF_KEY_NOTIFICATION, false)

    private val notificationSwitchListener: NotificationSwitchListener =
        NotificationSwitchListener { isChecked ->
            if (isNotificationPermissionGranted()) {
                sharedPreference.edit { putBoolean(PREF_KEY_NOTIFICATION, false) }
                binding.switchSettingPostNotification.isChecked = false
                showPermissionToast()
            } else {
                sharedPreference.edit { putBoolean(PREF_KEY_NOTIFICATION, isChecked) }
            }
            settingsPresenter.refreshChecked()
        }

    private fun isNotificationPermissionGranted(): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission.POST_NOTIFICATIONS,
            ) != PERMISSION_GRANTED
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
        private const val PREF_NAME = "settings"
        private const val PREF_KEY_NOTIFICATION = "notification"
    }
}
