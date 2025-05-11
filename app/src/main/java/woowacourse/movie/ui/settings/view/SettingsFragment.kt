package woowacourse.movie.ui.settings.view

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
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
        get() =
            sharedPrefs.getBoolean(getString(R.string.preference_post_notification), true)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        binding.isEnablePostNotification = isMovieReminderEnabled()
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        binding.switchPushNotification.setOnClickListener {
            toggleMovieNotification()
            binding.isEnablePostNotification = isEnablePostNotification
        }
    }

    private fun isMovieReminderEnabled(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val isGranted =
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS,
                ) == PackageManager.PERMISSION_GRANTED
            updateNotificationSetting(isGranted)
        }

        return isEnablePostNotification
    }

    private fun toggleMovieNotification() {
        updateNotificationSetting(!isEnablePostNotification)

        if (isEnablePostNotification) {
            // DB에서 알림 예약 가능한 모든 영화들을 예약
        } else {
            // 예약된 모든 영화 알림 해제
        }
    }

    private fun updateNotificationSetting(isEnabled: Boolean) {
        sharedPrefs.edit {
            putBoolean(getString(R.string.preference_post_notification), isEnabled)
        }
    }
}
