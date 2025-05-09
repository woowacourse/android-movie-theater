package woowacourse.movie.setting

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private val preference: SharedPreferences by lazy {
        requireContext().getSharedPreferences(
            SHARED_NAME,
            Context.MODE_PRIVATE,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.switchAlarm.isChecked = isNotificationPermissionGranted()

        binding.switchAlarm.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (!isPermitted()) {
                    requestNotificationPermission()
                    return@setOnCheckedChangeListener
                }
            }
            preference.edit {
                putBoolean(SHARED_SET_ALARM, isChecked)
            }
        }
    }

    private fun isNotificationPermissionGranted(): Boolean {
        val prefs = requireContext().getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(SHARED_SET_ALARM, false)
    }

    private fun isPermitted(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true

        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), "알림 설정 완료", Toast.LENGTH_SHORT).show()
                preference.edit {
                    putBoolean(SHARED_SET_ALARM, true)
                }
                binding.switchAlarm.isChecked = true
            } else {
                Toast.makeText(requireContext(), "알림 거부됨", Toast.LENGTH_SHORT).show()
                binding.switchAlarm.isChecked = false
            }
        }

    companion object {
        private const val SHARED_NAME = "settings"
        private const val SHARED_SET_ALARM = "notification"
    }
}
