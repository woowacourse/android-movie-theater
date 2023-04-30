package woowacourse.movie.fragment

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial
import woowacourse.movie.BuildConfig
import woowacourse.movie.NotificationPermission
import woowacourse.movie.R
import woowacourse.movie.SharedPreferenceUtil
import woowacourse.movie.activity.MainActivity

class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }
    private lateinit var switch: SwitchMaterial
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferenceUtil = SharedPreferenceUtil(view.context)
        switch = view.findViewById<SwitchMaterial>(R.id.switch_setting_can_push)
        switch.isChecked =
            sharedPreferenceUtil.getSettingValue(SETTING_PUSH_ALARM_SWITCH_KEY, false)

        switch.setOnCheckedChangeListener { _, _ ->
            NotificationPermission().requestNotificationPermission(view.context, ::getPermission)

            sharedPreferenceUtil.setSettingValue(
                SETTING_PUSH_ALARM_SWITCH_KEY,
                switch.isChecked
            )
        }
    }

    private fun getPermission() {
        switch.isChecked = false
        if (!shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.parse("package:" + BuildConfig.APPLICATION_ID))
            startActivity(intent)
        } else {
            (activity as MainActivity).requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    companion object {
        private const val SETTING_PUSH_ALARM_SWITCH_KEY = "settingPushAlarmSwitchKey"
    }
}
