package woowacourse.movie.feature.setting.view

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.feature.setting.MyReceiver
import woowacourse.movie.feature.setting.contract.SettingContract
import woowacourse.movie.feature.setting.presenter.SettingPresenter

class SettingFragment :
    Fragment(),
    SettingContract.View {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var presenter: SettingContract.Presenter
    private lateinit var alarmSetting: SharedPreferences

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted -> }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        alarmSetting = requireActivity().getSharedPreferences("alarmSetting", Context.MODE_PRIVATE)

        presenter = SettingPresenter(this, alarmSetting)

        binding.notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            presenter.toggleNotificationSwitch(isChecked)
        }

        presenter.loadNotificationSettings()
    }

    override fun setNotificationSwitchChecked(isChecked: Boolean) {
        binding.notificationSwitch.isChecked = isChecked
        if (isChecked) {
            showNotificationPermissionRequest()
        } else {
            val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, MyReceiver::class.java)
            val pendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
                )
            alarmManager.cancel(pendingIntent)
        }
    }

    override fun showNotificationPermissionRequest() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }
}
