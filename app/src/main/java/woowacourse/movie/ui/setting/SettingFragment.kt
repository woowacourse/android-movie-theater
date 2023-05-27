package woowacourse.movie.ui.setting

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.alarm.AlarmStateRepositoryImpl
import woowacourse.movie.data.reservation.ReservationRepositoryImpl
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.ui.alarm.ReservationAlarmManager
import woowacourse.movie.uimodel.MovieTicketModel

class SettingFragment : Fragment(), SettingContract.View {

    override val presenter by lazy {
        SettingPresenter(
            this,
            AlarmStateRepositoryImpl(requireContext()),
            ReservationRepositoryImpl(requireContext()),
        )
    }

    private lateinit var binding: FragmentSettingBinding

    private val reservationAlarmManager by lazy { ReservationAlarmManager(requireContext()) }
    private val requestPermissionLauncher by lazy {
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) {}
    }

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

        requestNotificationPermission(view)
        initToggleButton()
        setClickEventOnToggleButton()
    }

    private fun requestNotificationPermission(view: View) {
        if (ContextCompat.checkSelfPermission(
                view.context,
                android.Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ContextCompat.checkSelfPermission(
                    view.context,
                    android.Manifest.permission.POST_NOTIFICATIONS,
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                return
            }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                // 안드로이드 12 이하는 Notification에 관한 권한 필요 없음
                return
            }

            if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                // 권한 요청 거부한 경우
                return
            }

            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun initToggleButton() {
        presenter.checkSwitchState()
    }

    override fun setToggleButton(isChecked: Boolean) {
        binding.settingSwitch.isChecked = isChecked
    }

    private fun setClickEventOnToggleButton() {
        binding.settingSwitch.setOnCheckedChangeListener { _, isChecked ->
            presenter.clickSwitch(isChecked)
        }
    }

    override fun setAlarms(ticket: MovieTicketModel) {
        reservationAlarmManager.makeAlarm(ticket)
    }

    override fun cancelAlarms(ticket: MovieTicketModel) {
        reservationAlarmManager.cancelAlarm(ticket)
    }
}
