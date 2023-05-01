package woowacourse.movie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.service.Permission
import woowacourse.movie.service.Permission.checkNotificationPermission
import woowacourse.movie.service.Permission.requestNotificationPermission
import woowacourse.movie.view.setting.Setting
import woowacourse.movie.view.setting.SharedSetting

class SettingFragment : Fragment() {

    private val requestPermissionLauncher =
        Permission.getRequestPermissionLauncher(this, ::onPermissionGranted)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwitch(view)
    }

    private fun initSwitch(view: View) {
        val setting: Setting = SharedSetting(view.context)
        val switch = view.findViewById<SwitchCompat>(R.id.setting_push_switch)
        initSwitchCheckStatus(view)
        switch.setOnCheckedChangeListener { _, isChecked ->
            setting.setValue(SETTING_NOTIFICATION, isChecked)
            if (isChecked && !checkNotificationPermission(view)) {
                requestNotificationPermission(this, requestPermissionLauncher, ::requestPermission)
            }
        }
    }

    private fun initSwitchCheckStatus(view: View) {
        val setting: Setting = SharedSetting(view.context)
        val switch = view.findViewById<SwitchCompat>(R.id.setting_push_switch)
        if (!checkNotificationPermission(view)) {
            switch.isChecked = false
        } else {
            switch.isChecked = setting.getValue(SETTING_NOTIFICATION)
        }
    }

    private fun onPermissionGranted(view: View?) {
        view?.findViewById<SwitchCompat>(R.id.setting_push_switch)?.isChecked = false
    }

    private fun requestPermission(view: View) {
        Toast.makeText(
            view.context,
            view.context.getString(R.string.permission_instruction_ment),
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        const val SETTING_NOTIFICATION = "notification"
    }
}
