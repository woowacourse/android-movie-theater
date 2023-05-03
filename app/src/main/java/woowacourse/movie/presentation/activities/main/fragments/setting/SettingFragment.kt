package woowacourse.movie.presentation.activities.main.fragments.setting

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial
import com.woowacourse.data.local.PreferenceManager
import com.woowacourse.data.local.PreferenceManager.setBoolean
import woowacourse.movie.R
import woowacourse.movie.presentation.extensions.checkPermissionTiramisu
import woowacourse.movie.presentation.extensions.createAlertDialog
import woowacourse.movie.presentation.extensions.message
import woowacourse.movie.presentation.extensions.negativeButton
import woowacourse.movie.presentation.extensions.positiveButton
import woowacourse.movie.presentation.extensions.title

class SettingFragment : Fragment(R.layout.fragment_setting) {
    lateinit var pushSwitch: SwitchMaterial
    private val preferences by lazy { PreferenceManager.getInstance(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPushSwitch(view)
    }

    override fun onResume() {
        super.onResume()

        val isPushAllowed =
            preferences.getBoolean(PUSH_ALLOW_KEY, true) && checkPushPermission()

        pushSwitch.isChecked = isPushAllowed
        preferences.setBoolean(PUSH_ALLOW_KEY, isPushAllowed)
    }

    private fun initPushSwitch(view: View) {
        pushSwitch = view.findViewById(R.id.notification_push_switch)
        val isPushAllowed = preferences.getBoolean(PUSH_ALLOW_KEY, true) && checkPushPermission()

        pushSwitch.isChecked = preferences.getBoolean(PUSH_ALLOW_KEY, isPushAllowed)
        pushSwitch.setOnCheckedChangeListener { _, isAllowed ->
            if (isAllowed && !checkPushPermission()) {
                showPushPermissionDialog()
            } else {
                preferences.setBoolean(PUSH_ALLOW_KEY, isAllowed)
            }
        }
    }

    private fun checkPushPermission(): Boolean =
        requireContext().checkPermissionTiramisu(Manifest.permission.POST_NOTIFICATIONS)

    private fun showPushPermissionDialog() {
        requireContext().createAlertDialog {
            title(getString(R.string.permission_request_dialog_title))
            message(getString(R.string.permission_request_dialog_desc))
            positiveButton {
                val appDetailsIntent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:${requireContext().packageName}"),
                ).addCategory(Intent.CATEGORY_DEFAULT)
                startActivity(appDetailsIntent)
            }
            negativeButton { pushSwitch.isChecked = false }
        }.show()
    }

    companion object {
        private val settingFragment = SettingFragment()
        internal const val PUSH_ALLOW_KEY = "push_allow_key"

        fun getInstance(): SettingFragment = settingFragment
    }
}
