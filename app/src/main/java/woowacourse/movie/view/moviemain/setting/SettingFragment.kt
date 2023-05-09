package woowacourse.movie.view.moviemain.setting

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
import androidx.fragment.app.FragmentManager
import woowacourse.movie.R
import woowacourse.movie.data.reservation.ReservationDbRepository
import woowacourse.movie.data.setting.SettingPreferencesRepository
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.view.AlarmController
import woowacourse.movie.view.model.ReservationUiModel

class SettingFragment : Fragment(), SettingContract.View {

    private lateinit var alarmController: AlarmController
    private lateinit var binding: FragmentSettingBinding
    private lateinit var presenter: SettingContract.Presenter
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (!isGranted) {
                Toast.makeText(
                    requireContext(),
                    NOTICE_REQUEST_PERMISSION_MESSAGE,
                    Toast.LENGTH_LONG,
                ).show()
                setToggle(false)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        presenter = SettingPresenter(
            this,
            SettingPreferencesRepository(requireContext()),
            ReservationDbRepository(requireContext()),
        )
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        binding.presenter = presenter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        alarmController = AlarmController(requireContext())
        presenter.initState()
    }

    override fun setToggle(isOn: Boolean) {
        binding.settingToggle.isChecked = isOn
    }

    override fun setAlarms(reservations: List<ReservationUiModel>) {
        alarmController.registerAlarms(reservations, ALARM_MINUTE_INTERVAL)
    }

    override fun cancelAlarms() {
        alarmController.cancelAlarms()
    }

    override fun requestNotificationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                return ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS,
                ) == PackageManager.PERMISSION_GRANTED
            }
        }
        return true // android 12 version 이하는 notification 권한 필요 없기 때문
    }

    companion object {
        private const val NOTICE_REQUEST_PERMISSION_MESSAGE =
            "권한을 설정해야 알림을 받을 수 있습니다. 설정에서 알림을 켜주세요."
        const val ALARM_MINUTE_INTERVAL = 30L
        const val TAG_SETTING = "SETTING"

        fun of(supportFragmentManager: FragmentManager): SettingFragment {
            return supportFragmentManager.findFragmentByTag(TAG_SETTING) as? SettingFragment
                ?: SettingFragment()
        }
    }
}
