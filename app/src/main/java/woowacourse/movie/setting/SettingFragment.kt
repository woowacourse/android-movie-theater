package woowacourse.movie.setting

import android.Manifest
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
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.R
import woowacourse.movie.data.AlarmSharedPreferences
import woowacourse.movie.data.RoomMovieRepository
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    val binding: FragmentSettingBinding
        get() = requireNotNull(_binding) { "${this::class.java.simpleName}에서 에러가 발생했습니다." }

    private lateinit var presenter: SettingContract.Presenter

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

        presenter = SettingPresenter(RoomMovieRepository.instance(), requireContext())

        askNotificationPermission()
        alarmSwitchListener()
    }

    private fun askNotificationPermission() {
        if (requireActivity().checkSelfPermission(
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.should_get_alarm_right_message),
                        Snackbar.LENGTH_SHORT,
                    )
                        .show()
                    val intent =
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + requireContext().packageName))
                    startActivity(intent)
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            if (isGranted) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.alarm_right_get_message),
                    Snackbar.LENGTH_SHORT,
                ).show()
            } else {
                Snackbar.make(
                    binding.root,
                    getString(R.string.alarm_right_denied_message),
                    Snackbar.LENGTH_SHORT,
                ).show()
            }
        }

    private fun alarmSwitchListener() {
        val alarmSetting = AlarmSharedPreferences(requireContext())

        binding.switchSettingAlarm.setOnCheckedChangeListener { buttonView, isChecked ->
            alarmSetting.setAlarm(isChecked)
            if (isChecked) {
                presenter.setAlarm()
            } else {
                presenter.cancelAlarm()
            }
        }
        binding.switchSettingAlarm.isChecked = alarmSetting.isReservationAlarm()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
