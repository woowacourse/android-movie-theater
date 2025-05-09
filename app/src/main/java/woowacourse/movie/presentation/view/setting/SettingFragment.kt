package woowacourse.movie.presentation.view.setting

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.SettingPreferenceManager
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment :
    Fragment(),
    SettingContract.View {
    private lateinit var binding: FragmentSettingBinding
    private val presenter: SettingContract.Presenter by lazy {
        val preferenceManager = SettingPreferenceManager(requireContext())
        SettingPresenter(this, preferenceManager)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        presenter.fetchSettingInfo()
        setSwitchListener()
        return binding.root
    }

    private fun setSwitchListener() {
        binding.switchSettingPushAlarm.setOnCheckedChangeListener { _, isChecked ->
            presenter.savePushAlarmSetting(isChecked)
            if (isChecked) {
                requestNotificationPermission()
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            binding.switchSettingPushAlarm.setOnCheckedChangeListener(null)
            binding.switchSettingPushAlarm.isChecked = isGranted
            setSwitchListener()
        }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    showPermissionRationale()
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showPermissionRationale() {
        AlertDialog
            .Builder(requireContext())
            .setTitle(getString(R.string.setting_request_permission_dialog_title))
            .setMessage(R.string.setting_request_permission_dialog_message)
            .setPositiveButton(R.string.setting_request_permission_dialog_positive) { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }.setNegativeButton(R.string.setting_request_permission_dialog_negative) { _, _ ->
                binding.switchSettingPushAlarm.isChecked = false
            }.show()
    }

    override fun showPushAlarmSetting(isEnabled: Boolean) {
        binding.switchSettingPushAlarm.isChecked = isEnabled
    }
}
