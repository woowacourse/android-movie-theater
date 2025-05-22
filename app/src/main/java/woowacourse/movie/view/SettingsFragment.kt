package woowacourse.movie.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.App
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingsBinding
import woowacourse.movie.sharedPreference.SettingSharedPreferenceManager

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val prefs: SettingSharedPreferenceManager by lazy {
        (requireActivity().application as App).preferenceManager
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.scNotificationEnabled.isChecked = prefs.isNotificationEnabled()

        binding.scNotificationEnabled.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                requestNotificationPermission()
                return@setOnCheckedChangeListener
            }

            prefs.updateNotificationEnabled(false)
            binding.scNotificationEnabled.isChecked = false
        }
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    // 권한 요청 거부한 경우

                    return
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    return
                }
            }
        }

        prefs.updateNotificationEnabled(true)
        binding.scNotificationEnabled.isChecked = true
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            prefs.updateNotificationEnabled(isGranted)
            binding.scNotificationEnabled.isChecked = isGranted
        }
}
