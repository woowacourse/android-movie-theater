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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        presenter.fetchSettingInfo()
        initSwitchListener()
        return binding.root
    }

    override fun showPushAlarmSetting(isEnabled: Boolean) {
        binding.switchSettingPushAlarm.isChecked = isEnabled
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            updateSwitchState(isGranted)

            if (!isGranted && shouldShowPermissionRationale()) {
                showPermissionRationaleDialog()
            }
        }

    private fun initSwitchListener() {
        binding.switchSettingPushAlarm.setOnCheckedChangeListener { _, isChecked ->
            presenter.savePushAlarmSetting(isChecked)

            if (isChecked && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestNotificationPermission()
            }
        }
    }

    private fun requestNotificationPermission() {
        if (isNotificationPermissionGranted()) return

        if (shouldShowPermissionRationale()) {
            showPermissionRationaleDialog()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun isNotificationPermissionGranted(): Boolean =
        Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED

    private fun shouldShowPermissionRationale(): Boolean =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)

    private fun updateSwitchState(isGranted: Boolean) {
        binding.switchSettingPushAlarm.setOnCheckedChangeListener(null)
        binding.switchSettingPushAlarm.isChecked = isGranted
        initSwitchListener()
    }

    private fun showPermissionRationaleDialog() {
        AlertDialog
            .Builder(requireContext())
            .setTitle(getString(R.string.setting_request_permission_dialog_title))
            .setMessage(R.string.setting_request_permission_dialog_message)
            .setPositiveButton(R.string.setting_request_permission_dialog_positive) { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }.setNegativeButton(R.string.setting_request_permission_dialog_negative) { _, _ ->
                updateSwitchState(false)
            }.show()
    }
}
