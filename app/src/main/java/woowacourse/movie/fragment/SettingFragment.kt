package woowacourse.movie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.Permission
import woowacourse.movie.Permission.checkNotificationPermission
import woowacourse.movie.Permission.requestNotificationPermission
import woowacourse.movie.R
import woowacourse.movie.Setting
import woowacourse.movie.SharedSetting
import woowacourse.movie.view.error.FragmentError.finishWithNullViewError

class SettingFragment : Fragment() {

    private val requestPermissionLauncher =
        Permission.getRequestPermissionLauncher(this, ::onPermissionGranted)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        makeNotificationSwitchCompat(view)
        return view
    }

    override fun onResume() {
        super.onResume()
        val view: View = view ?: return finishWithNullViewError()
        val setting: Setting = SharedSetting(view.context)
        val switch = view.findViewById<SwitchCompat>(R.id.setting_push_switch)
        if (!checkNotificationPermission(view)) switch.isChecked = false
        else switch.isChecked = setting.getValue(SETTING_NOTIFICATION)
    }

    private fun makeNotificationSwitchCompat(view: View) {
        val setting: Setting = SharedSetting(view.context)
        val switch = view.findViewById<SwitchCompat>(R.id.setting_push_switch)
        switch.setOnCheckedChangeListener { _, isChecked ->
            setting.setValue(SETTING_NOTIFICATION, isChecked)
            if (isChecked && !checkNotificationPermission(view)) {
                requestNotificationPermission(this, requestPermissionLauncher, ::requestPermission)
            }
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
