package woowacourse.movie.presentation.activities.main.fragments.setting

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial
import com.woowacourse.data.local.PreferenceManager
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.presentation.extensions.checkPermission
import woowacourse.movie.presentation.extensions.createAlertDialog
import woowacourse.movie.presentation.extensions.message
import woowacourse.movie.presentation.extensions.negativeButton
import woowacourse.movie.presentation.extensions.positiveButton
import woowacourse.movie.presentation.extensions.title

class SettingFragment : Fragment(), SettingContract.View {
    override lateinit var presenter: SettingPresenter
    private lateinit var binding: FragmentSettingBinding
    private lateinit var pushSwitch: SwitchMaterial

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = SettingPresenter(this)

        pushSwitch = binding.notificationPushSwitch
        initPushSwitch()
    }

    private fun initPushSwitch() {
        val isPushAllowed = presenter.getPushAllowPreference(PUSH_ALLOW_KEY, true) && isPermittedPushPermission()

        pushSwitch.isChecked = presenter.getPushAllowPreference(PUSH_ALLOW_KEY, isPushAllowed)
        pushSwitch.setOnCheckedChangeListener { _, isAllowed ->
            presenter.onSwitchChanged(isPermittedPushPermission(), isAllowed)
        }
    }

    override fun getSharedPreference(): SharedPreferences {
        return PreferenceManager.getInstance(requireContext())
    }

    override fun onResume() {
        super.onResume()

        val isPushAllowed =
            presenter.getPushAllowPreference(PUSH_ALLOW_KEY, true) && isPermittedPushPermission()

        pushSwitch.isChecked = isPushAllowed
        presenter.setPushAllowPreference(PUSH_ALLOW_KEY, isPushAllowed)
    }

    private fun isPermittedPushPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireContext().checkPermission(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            true
        }
    }

    override fun showPushPermissionDialog() {
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
        internal const val PUSH_ALLOW_KEY = "push_allow_key"
    }
}
