package woowacourse.movie.view.moviemain.setting

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial
import woowacourse.movie.R
import woowacourse.movie.util.isGranted
import woowacourse.movie.util.requestRequiredPermissions
import woowacourse.movie.view.alarm.ReservationAlarmManager
import woowacourse.movie.view.model.ReservationUiModel

class SettingFragment : Fragment(R.layout.fragment_setting), SettingContract.View {

    override lateinit var presenter: SettingContract.Presenter
    private lateinit var reservationAlarmManager: ReservationAlarmManager
    private lateinit var toggle: SwitchMaterial
    private var initialToggleValue: Boolean = false

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            presenter.setAlarmPreference(true)
        } else {
            Toast.makeText(
                requireContext(),
                "권한을 설정해야 알림을 받을 수 있습니다. 설정에서 알림을 켜주세요.",
                Toast.LENGTH_LONG
            ).show()
            presenter.setAlarmPreference(false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = SettingPresenterFactory(
            this, requireActivity().applicationContext
        ).createSettingPresenter()

        reservationAlarmManager = ReservationAlarmManager(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toggle = view.findViewById(R.id.setting_toggle)
        toggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) requestNotificationPermission()
        }
        presenter.loadAlarmSetting()
    }

    override fun initToggleState(isAlarmOn: Boolean) {
        initialToggleValue = isAlarmOn
        toggle.isChecked = isAlarmOn
    }

    override fun setToggleState(isAlarmOn: Boolean) {
        toggle.isChecked = isAlarmOn
    }

    private fun requestNotificationPermission(): Boolean {
        val permissionsRequired = mutableListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) permissionsRequired.add(Manifest.permission.POST_NOTIFICATIONS)
        requireActivity().requestRequiredPermissions(
            permissionsRequired,
            requestPermissionLauncher::launch
        )
        return requireActivity().isGranted(Manifest.permission.POST_NOTIFICATIONS)
    }

    override fun onStop() {
        super.onStop()
        val isToggleChecked = toggle.isChecked
        if (initialToggleValue != isToggleChecked) {
            applyChange(isToggleChecked)
        }
    }

    private fun applyChange(isToggleChecked: Boolean) {
        if (isToggleChecked) {
            presenter.resetAlarms()
        } else {
            reservationAlarmManager.cancelAlarms()
            presenter.setAlarmPreference(false)
        }
    }

    override fun resetAlarms(reservations: List<ReservationUiModel>, timeInterval: Long) {
        reservationAlarmManager.registerAlarms(reservations, timeInterval)
    }
}
