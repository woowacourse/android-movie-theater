package woowacourse.movie.setting

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import woowacourse.movie.R
import woowacourse.movie.base.BindingFragment
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment : BindingFragment<FragmentSettingBinding>(R.layout.fragment_setting) {
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupNotificationSwitch()
    }

    private fun setupNotificationSwitch() {
        val notificationManager =
            ContextCompat.getSystemService(
                requireContext(),
                NotificationManager::class.java,
            ) as NotificationManager

        val areNotificationsEnabled = notificationManager.areNotificationsEnabled()

        binding.switchNotification.apply {
            isChecked = areNotificationsEnabled
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    val intent =
                        Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                            putExtra(Settings.EXTRA_APP_PACKAGE, requireContext().packageName)
                        }
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "알림을 끄려면 설정에서 끄세요.", Toast.LENGTH_LONG).show()
                    val intent =
                        Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                            putExtra(Settings.EXTRA_APP_PACKAGE, requireContext().packageName)
                        }
                    startActivity(intent)
                }
            }
        }
    }
}
