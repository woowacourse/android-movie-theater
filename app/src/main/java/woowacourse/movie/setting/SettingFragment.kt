package woowacourse.movie.setting

import android.Manifest
import android.app.AlarmManager
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity.ALARM_SERVICE
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.SettingPreference
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment : Fragment(), SettingContract.View {
    private val presenter: SettingPresenter by lazy {
        SettingPresenter(
            this,
            SettingPreference(requireContext()),
        )
    }
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

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

        presenter.setPermissionState()
    }

    override fun onResume() {
        super.onResume()
        val isPermitted = isPermittedExactAlarm() && isPermittedPostNotification()
        setAlarmState(isPermitted)
        presenter.updatePermission(isPermitted)
    }

    override fun initAlarmState(isGrant: Boolean) {
        setAlarmState(isGrant)
        binding.switchAlarm.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && !isPermittedPostNotification()) {
                requestNotificationPermission()
                return@setOnCheckedChangeListener
            } else if (isChecked && !isPermittedExactAlarm()) {
                showScheduleExtractAlarmPermissionDialog()
                return@setOnCheckedChangeListener
            }
            presenter.updatePermission(isChecked)
        }
    }

    private fun setAlarmState(isGrant: Boolean) {
        binding.switchAlarm.isChecked = isGrant
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                showNotificationPermissionDialog()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun showNotificationPermissionDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dig_notification_permission_title))
            .setMessage(getString(R.string.dig_permission_message))
            .setPositiveButton(getString(R.string.dig_permission_positive_message)) { _, _ ->
                val intent =
                    Intent().apply {
                        action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                        putExtra(Settings.EXTRA_APP_PACKAGE, "woowacourse.movie")
                    }
                startActivity(intent)
            }
            .setNegativeButton(getString(R.string.dig_permission_negative_message)) { dialog, _ ->
                dialog.dismiss()
                setAlarmState(false)
            }
            .setCancelable(false)
            .show()
    }

    private fun showScheduleExtractAlarmPermissionDialog() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dig_exact_alarm_permission_title))
            .setMessage(getString(R.string.dig_permission_message))
            .setPositiveButton(getString(R.string.dig_permission_positive_message)) { _, _ ->
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                startActivity(intent)
            }
            .setNegativeButton(getString(R.string.dig_permission_negative_message)) { dialog, _ ->
                dialog.dismiss()
                setAlarmState(false)
            }
            .setCancelable(false)
            .show()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            if (isGranted) {
                showScheduleExtractAlarmPermissionDialog()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.text_post_notification_permission_denied),
                    Toast.LENGTH_SHORT,
                ).show()
                setAlarmState(false)
            }
        }

    private fun isPermittedPostNotification(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true

        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isPermittedExactAlarm(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true

        val alarmManager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager
        return alarmManager.canScheduleExactAlarms()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
