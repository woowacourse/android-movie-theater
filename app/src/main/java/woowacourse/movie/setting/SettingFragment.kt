package woowacourse.movie.reservationlist

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import woowacourse.MovieAlarmManager
import woowacourse.PreferenceManager
import woowacourse.movie.MovieApplication
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.repository.SettingRepository
import woowacourse.movie.setting.SettingContract
import woowacourse.movie.setting.SettingPresenter
import woowacourse.movie.setting.SwitchListener
import woowacourse.movie.util.buildFetchAllReservationsUseCase

class SettingFragment :
    Fragment(),
    SettingContract.View,
    SwitchListener {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var presenter: SettingContract.Presenter
    private lateinit var alarmMgr: AlarmManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        binding.listener = this
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val movieApplication = (requireActivity().application as MovieApplication)
        alarmMgr = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val db = movieApplication.db
        val fetchAllReservationsUseCase = buildFetchAllReservationsUseCase(db)
        val settingRepository: SettingRepository = PreferenceManager(requireContext())
        val movieAlarmManager: MovieAlarmManager =
            MovieAlarmManager(requireContext(), fetchAllReservationsUseCase)
        presenter = SettingPresenter(this, settingRepository, movieAlarmManager)
        presenter.initSetting()
    }

    override fun requestPermission() {
        requestNotificationPermission()
        requestExactAlarmPermission()
    }

    override fun showChecked(checked: Boolean) {
        binding.checked = checked
    }

    override fun onClick() {
        presenter.toggleAlarm()
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    // 권한 요청 거부한 경우
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                // 안드로이드 12 이하는 Notification에 관한 권한 필요 없음
            }
        }
    }

    private fun requestExactAlarmPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.SCHEDULE_EXACT_ALARM,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.SCHEDULE_EXACT_ALARM)) {
                    // 권한 요청 거부한 경우
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.SCHEDULE_EXACT_ALARM)
                }
                if (!alarmMgr.canScheduleExactAlarms()) {
                    val intent =
                        Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                            data = Uri.fromParts("package", requireContext().packageName, null)
                        }
                    requireContext().startActivity(intent)
                }
            } else {
                // 안드로이드 12 이하는 schedule alarm에 관한 권한 필요 없음
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean -> }
}
