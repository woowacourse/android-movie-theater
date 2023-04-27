package woowacourse.movie.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial
import woowacourse.movie.BundleKeys
import woowacourse.movie.R
import woowacourse.movie.SharedPreferenceUtil
import woowacourse.movie.activity.MainActivity

class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        val switch = view.findViewById<SwitchMaterial>(R.id.sw_setting_can_push)
        val sharedPreferenceUtil = SharedPreferenceUtil(view.context)

        switch.isChecked =
            sharedPreferenceUtil.getSettingValue(BundleKeys.SETTING_PUSH_ALARM_SWITCH_KEY, false)

        switch.setOnCheckedChangeListener { _, _ ->
            if (ContextCompat.checkSelfPermission(
                    inflater.context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_DENIED
            ) {
                switch.isChecked = false
                if (!shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    switch.isEnabled = false
                } else {
                    (activity as MainActivity).requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }

            sharedPreferenceUtil.setSettingValue(
                BundleKeys.SETTING_PUSH_ALARM_SWITCH_KEY,
                switch.isChecked
            )
        }
        return view
    }
}
