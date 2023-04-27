package woowacourse.movie.presentation.activities.main.fragments.setting

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial
import woowacourse.movie.R
import woowacourse.movie.presentation.MovieApplication
import woowacourse.movie.presentation.extensions.checkPermissionTiramisu
import woowacourse.movie.presentation.extensions.createAlertDialog
import woowacourse.movie.presentation.extensions.message
import woowacourse.movie.presentation.extensions.negativeButton
import woowacourse.movie.presentation.extensions.positiveButton
import woowacourse.movie.presentation.extensions.title

class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPushSwitch(view)
    }

    @SuppressLint("InlinedApi")
    private fun initPushSwitch(view: View) {
        val switch = view.findViewById<SwitchMaterial>(R.id.notification_push_switch)
        val preferences = MovieApplication.preferences
        val isPushAllowed = preferences.getBoolean(PUSH_ALLOW_KEY, true) &&
            requireContext().checkPermissionTiramisu(Manifest.permission.POST_NOTIFICATIONS)

        with(switch) {
            isChecked = preferences.getBoolean(PUSH_ALLOW_KEY, isPushAllowed)
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked && !requireContext().checkPermissionTiramisu(Manifest.permission.POST_NOTIFICATIONS)) {
                    requireContext().createAlertDialog {
                        title("권한 요청")
                        message("푸시 알림을 받으려면 권한이 필요합니다.")
                        positiveButton {
                            // 권한을 요청한다.
                        }
                        negativeButton { setChecked(false) }
                    }.show()
                } else {
                    preferences.setBoolean(PUSH_ALLOW_KEY, isChecked)
                }
            }
        }
    }

    companion object {
        private val settingFragment = SettingFragment()
        internal const val PUSH_ALLOW_KEY = "push_allow_key"

        fun newInstance(): SettingFragment = settingFragment.apply {
            arguments = Bundle().apply {
            }
        }
    }
}
