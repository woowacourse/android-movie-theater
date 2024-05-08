package woowacourse.movie.feature.setting

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.util.MovieIntentConstant.KEY_NOTIFICATION
import woowacourse.movie.util.SharedPreferencesManager

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        requestNotificationPermission()
    }

    private fun requestNotificationPermission() {
        if (checkRequestNotificationPermission()) {
            binding.layoutNotificationSwitch.visibility = View.GONE
            binding.layoutNotificationSetting.visibility = View.VISIBLE
            binding.btnPermission.setOnClickListener {
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, requireContext().packageName)
                startActivity(intent)
            }

            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            return
        }

        binding.layoutNotificationSwitch.visibility = View.VISIBLE
        binding.layoutNotificationSetting.visibility = View.GONE
    }

    private fun checkRequestNotificationPermission(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return false
        }
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return true
    }

    private fun initializeNotificationSwitch() {
        binding.switchNotification.setOnCheckedChangeListener { _, isChecked ->
            SharedPreferencesManager(requireContext()).setBoolean(KEY_NOTIFICATION, isChecked)
            if (isChecked) {

                return@setOnCheckedChangeListener
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
