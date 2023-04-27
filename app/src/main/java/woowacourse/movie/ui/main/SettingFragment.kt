package woowacourse.movie.ui.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.util.SettingSharedPreference

class SettingFragment : Fragment() {
    private val switch: Switch by lazy {
        requireActivity().findViewById(R.id.switchPushAlarm)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            switch.isChecked = isGranted
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val settingSharePreference = SettingSharedPreference(view.context)
        setSwitch(settingSharePreference)
    }

    private fun setSwitch(settingSharePreference: SettingSharedPreference) {
        switch.isChecked = settingSharePreference.receivingPushAlarm
        setSwitchCheckedChange(switch.context, settingSharePreference)
    }

    private fun setSwitchCheckedChange(
        context: Context,
        settingSharePreference: SettingSharedPreference,
    ) {
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                requestNotificationPermission(context)
            }
            settingSharePreference.receivingPushAlarm = isChecked
        }
    }

    private fun requestNotificationPermission(context: Context) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_DENIED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}
