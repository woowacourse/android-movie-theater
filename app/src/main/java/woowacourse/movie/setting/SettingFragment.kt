package woowacourse.movie.setting

import android.Manifest
import android.app.AlarmManager
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

        presenter.setPermissionState(requireContext())
    }

    override fun initAlarmState(isGrant: Boolean) {
        setAlarmState(isGrant)
        binding.switchAlarm.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && !isPermitted()) {
                requestNotificationPermission()
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
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.text_permission_request, "Alarms"),
                    Toast.LENGTH_SHORT,
                ).show()
                requestExactAlarmPermission()
                presenter.updatePermission(true)
                setAlarmState(true)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.text_permission_request, "Notifications"),
                    Toast.LENGTH_SHORT,
                ).show()
                setAlarmState(false)
            }
        }

    private fun requestExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                startActivity(intent)
            }
        }
    }

    private fun isPermitted(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true

        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
