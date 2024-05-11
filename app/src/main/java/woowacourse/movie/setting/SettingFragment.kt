package woowacourse.movie.reservationlist

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.AlarmReceiver
import woowacourse.movie.MovieApplication
import woowacourse.movie.data.DummyEverythingRepository
import woowacourse.movie.data.reservationref.ReservationRefRepositoryImpl
import woowacourse.movie.data.screeningref.ScreeningRefRepositoryImpl
import woowacourse.movie.data.theater.TheaterRepositoryImpl
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.setting.SettingContract
import woowacourse.movie.setting.SettingPresenter
import woowacourse.movie.setting.SwitchListener
import woowacourse.movie.setting.uimodel.ReservationAlarmUiModel
import woowacourse.movie.usecase.FetchAllReservationsUseCase
import woowacourse.movie.usecase.FetchScreeningWithIdUseCase

class SettingFragment : Fragment(), SettingContract.View, SwitchListener {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var presenter: SettingContract.Presenter
    private lateinit var movieApplication: MovieApplication
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
        movieApplication = (requireActivity().application as MovieApplication)
        alarmMgr = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        presenter = SettingPresenter(this, buildFetchAllReservationsUseCase())
        val sharedPreference = requireContext().getSharedPreferences("settings", MODE_PRIVATE)
        val value = sharedPreference.getBoolean("notification", false)
        presenter.initSetting(value)
        binding.checked = value
    }

    private fun buildFetchAllReservationsUseCase(): FetchAllReservationsUseCase {
        val db = (requireActivity().application as MovieApplication).db
        val reservationRefRepository = ReservationRefRepositoryImpl(db.reservationDao())
        val theaterRepository = TheaterRepositoryImpl(db.theaterDao())
        val screeningRefRepository = ScreeningRefRepositoryImpl(db.screeningDao())
        val screeningWithIdUseCase =
            FetchScreeningWithIdUseCase(
                DummyEverythingRepository,
                theaterRepository,
                screeningRefRepository,
            )
        return FetchAllReservationsUseCase(reservationRefRepository, screeningWithIdUseCase)
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

    private fun requestScheduleExactAlarmPermission() {
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
                        Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
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

    override fun turnOnAlarm(reservationAlarmUiModels: List<ReservationAlarmUiModel>) {
        requestNotificationPermission()
        requestScheduleExactAlarmPermission()
        val sharedPreference = requireContext().getSharedPreferences("settings", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putBoolean("notification", true).apply()
        val value = sharedPreference.getBoolean("notification", true)
        binding.checked = value
        setAlarmAt(reservationAlarmUiModels)
    }

    private fun setAlarmAt(reservationAlarmUiModels: List<ReservationAlarmUiModel>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !alarmMgr.canScheduleExactAlarms()) return
        reservationAlarmUiModels.forEach {
            val intent = AlarmReceiver.getIntent(requireContext(), it.id, it.title)
            val requestCode = it.id.toInt()
            val pendingIntent = getPendingIntent(requestCode, intent)
            val alarmClock = AlarmManager.AlarmClockInfo(it.alarmTime, pendingIntent)
            alarmMgr.setAlarmClock(alarmClock, pendingIntent)
        }
    }

    override fun turnOffAlarm(reservationAlarmUiModels: List<ReservationAlarmUiModel>) {
        val sharedPreference = requireContext().getSharedPreferences("settings", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putBoolean("notification", false).apply()
        val value = sharedPreference.getBoolean("notification", true)
        binding.checked = value
        cancelAlarm(reservationAlarmUiModels)
    }

    private fun cancelAlarm(reservationAlarmUiModels: List<ReservationAlarmUiModel>) {
        reservationAlarmUiModels.forEach {
            val intent = Intent(requireContext(), AlarmReceiver::class.java)
            val requestCode = it.id.toInt()
            val alarmIntent = getPendingIntent(requestCode, intent)
            alarmMgr.cancel(alarmIntent)
        }
    }

    private fun getPendingIntent(
        requestCode: Int,
        intent: Intent,
    ): PendingIntent =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(
                requireContext(),
                requestCode,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
            )
        } else {
            PendingIntent.getBroadcast(
                requireContext(),
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT,
            )
        }

    override fun onClick() {
        presenter.toggleAlarm()
    }
}
