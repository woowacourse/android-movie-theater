package woowacourse.movie.presentation.views.main.fragments.setting

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.woowacourse.data.datasource.cache.local.LocalCacheDataSource
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.presentation.extensions.checkPermissionTiramisu
import woowacourse.movie.presentation.extensions.createAlertDialog
import woowacourse.movie.presentation.extensions.message
import woowacourse.movie.presentation.extensions.negativeButton
import woowacourse.movie.presentation.extensions.positiveButton
import woowacourse.movie.presentation.extensions.title
import woowacourse.movie.presentation.views.main.fragments.setting.contract.SettingContract
import woowacourse.movie.presentation.views.main.fragments.setting.contract.presenter.SettingPresenter

class SettingFragment : Fragment(), SettingContract.View {
    override val presenter: SettingContract.Presenter by lazy {
        SettingPresenter(
            view = this,
            cacheDataSource = LocalCacheDataSource.getInstance(requireContext())
        )
    }
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val settingScreenLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val isPushPermissionGranted = requireContext()
                .checkPermissionTiramisu(Manifest.permission.POST_NOTIFICATIONS)
            presenter.updatePushAllow(isPushPermissionGranted)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        binding.presenter = presenter
        return binding.root
    }

    override fun changePushSwitchState(newState: Boolean) {
        binding.notificationPushSwitch.isChecked = newState
    }

    override fun checkPushPermission(): Boolean =
        requireContext().checkPermissionTiramisu(Manifest.permission.POST_NOTIFICATIONS)

    override fun showPushPermissionDialog() {
        requireContext().createAlertDialog {
            title(getString(R.string.permission_request_dialog_title))
            message(getString(R.string.permission_request_dialog_desc))
            positiveButton { launchSettingScreen() }
            negativeButton { presenter.updatePushAllow(false) }
        }.show()
    }

    private fun launchSettingScreen() {
        val appDetailsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:${requireContext().packageName}")
        ).addCategory(Intent.CATEGORY_DEFAULT)
        settingScreenLauncher.launch(appDetailsIntent)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        internal const val TAG = "SettingFragment"
    }
}
