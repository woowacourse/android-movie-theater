package woowacourse.movie.ui.settings.view

import android.Manifest.permission
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        val sharedPreference = requireContext().getSharedPreferences("settings", MODE_PRIVATE)
        val notificationFlag = sharedPreference.getBoolean("notification", false)
        binding.switchSettingPostNotification.isChecked = notificationFlag

        binding.notificationSwitchListener =
            NotificationSwitchListener { isChecked ->
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        permission.POST_NOTIFICATIONS,
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    sharedPreference.edit { putBoolean("notification", false) }
                    binding.switchSettingPostNotification.isChecked = false
                    Toast
                        .makeText(requireContext(), "설정에서 알림 권한을 요청해야 합니다.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    when (isChecked) {
                        true -> {
                            val editor: SharedPreferences.Editor = sharedPreference.edit()
                            editor.putBoolean("notification", true).apply()
                        }

                        false -> {
                            val editor: SharedPreferences.Editor = sharedPreference.edit()
                            editor.putBoolean("notification", false).apply()
                        }
                    }

                    val value = sharedPreference.getBoolean("notification", false)
                    Log.d("SH_PREF", "$value")
                }
            }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
