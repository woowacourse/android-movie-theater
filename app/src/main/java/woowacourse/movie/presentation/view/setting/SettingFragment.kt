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
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment :
    Fragment(),
    SettingContract.View {
    private lateinit var binding: FragmentSettingBinding
    private val presenter: SettingContract.Presenter by lazy {
        SettingPresenter(
            this,
            requireContext(),
        )
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
            .setTitle("알림 권한 필요")
            .setMessage("알림 기능을 사용하려면 권한이 필요합니다.")
            .setPositiveButton("권한 요청") { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }.setNegativeButton("취소") { _, _ ->
                binding.switchSettingPushAlarm.isChecked = false
            }.show()
    }

    override fun showPushAlarmSetting(isEnabled: Boolean) {
        binding.switchSettingPushAlarm.isChecked = isEnabled
    }
}
