package woowacourse.movie.presentation.activities.main.fragments.setting

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private var _binding: FragmentSettingBinding? = null
    private val binding: FragmentSettingBinding
        get() = _binding!!
    override lateinit var presenter: SettingContract.Presenter
    private lateinit var pushSwitch: SwitchMaterial

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPresenter()
        pushSwitch = binding.notificationPushSwitch
        initPushSwitch()
    }

    private fun initPresenter() {
        val sharedPreferences = PreferenceManager.getInstance(requireContext())
        val alarmRepository = AlarmRepository(sharedPreferences)
        presenter = SettingPresenter(this, alarmRepository)
    }

    private fun initPushSwitch() {
        val isPushAllowed = presenter.getPushAllow(true) && isPermittedPushPermission()

        pushSwitch.isChecked = presenter.getPushAllow(isPushAllowed)
        pushSwitch.setOnCheckedChangeListener { _, isAllowed ->
            presenter.onSwitchChanged(isPermittedPushPermission(), isAllowed)
        }
    }

    override fun onResume() {
        super.onResume()

        val isPushAllowed =
            presenter.getPushAllow(true) && isPermittedPushPermission()

        pushSwitch.isChecked = isPushAllowed
        presenter.setPushAllow(isPushAllowed)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
