package woowacourse.movie.ui.setting

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.MovieTheaterSharedPreference
import woowacourse.movie.ReservationHistoryAlarmManager
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.db.reservationhistory.ReservationHistoryDatabase

class SettingFragment : Fragment(), SettingContract.View {
    private var _binding: FragmentSettingBinding? = null
    private val binding
        get() = requireNotNull(_binding)
    private val movieTheaterSharedPreference by lazy {
        MovieTheaterSharedPreference.getInstance(requireActivity().application)
    }
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {}
    private val reservationHistoryAlarm by lazy { ReservationHistoryAlarmManager(requireContext()) }
    private val presenter: SettingContract.Presenter by lazy {
        SettingPresenter(
            this,
            ReservationHistoryDatabase.getInstance(requireContext()),
            reservationHistoryAlarm,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initNotification()
        binding.movieTheaterSharedPreference = movieTheaterSharedPreference
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onStart() {
        super.onStart()
        requestNotificationPermission()
    }

    private fun initNotification() {
        binding.receivePushNotificationSelect.isChecked = movieTheaterSharedPreference.notificationEnabled
    }

    private fun onClickNotification() {
        binding.receivePushNotificationSelect.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                presenter.confirmAlarm()
                movieTheaterSharedPreference.notificationEnabled = !movieTheaterSharedPreference.notificationEnabled
            } else {
                presenter.cancelAram()
                movieTheaterSharedPreference.notificationEnabled = !movieTheaterSharedPreference.notificationEnabled
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        if (checkRequestNotificationPermission()) {
            binding.receivePushNotificationSelect.isEnabled = false

            Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, requireContext().packageName)
                startActivity(this)
            }

            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            binding.receivePushNotificationSelect.isChecked = true
            onClickNotification()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkRequestNotificationPermission(): Boolean {
        return !(
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
        )
    }
}
