package woowacourse.movie.ui.settings.view

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val sharedPrefs: SharedPreferences by lazy {
        requireContext().getSharedPreferences(getString(R.string.preference_key), MODE_PRIVATE)
    }
    private val isEnablePostNotification: Boolean
        get() = sharedPrefs.getBoolean(getString(R.string.preference_post_notification), true)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        syncNotificationPermissionWithPrefsAndUI()
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.switchPushNotification.setOnClickListener {
            updateNotificationSetting(!isEnablePostNotification)
        }
    }

    override fun onResume() {
        super.onResume()
        syncNotificationPermissionWithPrefsAndUI()
    }

    private fun syncNotificationPermissionWithPrefsAndUI()  {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val isGranted =
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS,
                ) == PackageManager.PERMISSION_GRANTED
            sharedPrefs.edit {
                putBoolean(getString(R.string.preference_post_notification), isGranted)
            }
        }

        binding.isEnablePostNotification = isEnablePostNotification
    }

    private fun updateNotificationSetting(isEnabled: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val intent =
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", requireContext().packageName, null)
                }
            startActivity(intent)
            return
        }

        sharedPrefs.edit {
            putBoolean(getString(R.string.preference_post_notification), isEnabled)
        }
        binding.isEnablePostNotification = isEnabled
    }
}
