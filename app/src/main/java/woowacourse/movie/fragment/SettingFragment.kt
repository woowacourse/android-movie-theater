package woowacourse.movie.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.Setting
import woowacourse.movie.SharedSetting
import woowacourse.movie.view.error.FragmentError.finishWithNullViewError

class SettingFragment : Fragment() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                view?.findViewById<SwitchCompat>(R.id.setting_push_switch)?.isChecked = false
            }
        }

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
                requestNotificationPermission(view)
            }
        }
    }

    private fun requestNotificationPermission(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            if (!shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                showPermissionInstruction(view)
            }
        }
    }

    private fun showPermissionInstruction(view: View) {
        Toast.makeText(
            view.context,
            getString(R.string.permission_instruction_ment),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun checkNotificationPermission(view: View): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                view.context, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else false
    }

    companion object {
        const val SETTING_NOTIFICATION = "notification"
    }
}
