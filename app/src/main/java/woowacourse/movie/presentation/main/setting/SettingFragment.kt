package woowacourse.movie.presentation.main.setting

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.repository.MovieTicketRepositoryImpl
import woowacourse.movie.data.utils.SharedPreferencesHelper
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment : Fragment(), SettingContract.View {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var presenter: SettingContract.Presenter
    private var toast: Toast? = null

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        presenter.onPermissionResult(isGranted)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)

        val repository = MovieTicketRepositoryImpl(requireContext())
        presenter = SettingPresenter(this, repository)

        binding.alarmSettingSwitch.setOnCheckedChangeListener { _, isChecked ->
            presenter.onAlarmSwitchChanged(isChecked)
        }
        initSettingView()

        presenter.initializeSettings()

        return binding.root
    }

    private fun initSettingView() {
        binding.alarmSettingSwitch.setOnCheckedChangeListener { _, isChecked ->
            presenter.onAlarmSwitchChanged(isChecked)
        }
        binding.mode = SharedPreferencesHelper.isNotificationEnabled(requireContext())

        presenter.initializeSettings()
    }

    override fun requestNotificationPermission() {
        activity?.let {
            if (ContextCompat.checkSelfPermission(it, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                        showMessage("알림 권한이 필요합니다.")
                    }
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    SharedPreferencesHelper.setNotificationEnabled(it, true)
                    presenter.updateAllNotifications()
                }
            } else {
                SharedPreferencesHelper.setNotificationEnabled(it, true)
                presenter.updateAllNotifications()
            }
        }
    }

    override fun updateAllNotifications() {
        presenter.updateAllNotifications()
    }

    override fun cancelAllNotifications() {
        presenter.cancelAllNotifications()
    }

    override fun showMessage(message: String) {
        toast?.cancel()
        toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun setAlarmSwitchChecked(isChecked: Boolean) {
        binding.alarmSettingSwitch.isChecked = isChecked
    }
}
