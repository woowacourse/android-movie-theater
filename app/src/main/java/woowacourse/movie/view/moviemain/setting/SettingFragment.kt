package woowacourse.movie.view.moviemain.setting

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial
import woowacourse.movie.R
import woowacourse.movie.data.setting.SettingPreferencesManager
import woowacourse.movie.view.model.AlarmController

class SettingFragment : Fragment(R.layout.fragment_setting), SettingContract.View {

    private lateinit var toggle: SwitchMaterial
    override lateinit var presenter: SettingContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = SettingPresenter(this, SettingPreferencesManager(requireContext()), AlarmController(requireContext()))

        toggle = view.findViewById(R.id.setting_toggle)
        presenter.initToggle()
        toggle.setOnCheckedChangeListener { _, isChecked ->
            presenter.onClick(isChecked)
        }
    }

    override fun setToggle(isOn: Boolean) {
        toggle.isChecked = isOn
    }

    override val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (!isGranted) {
            Toast.makeText(requireContext(), "권한을 설정해야 알림을 받을 수 있습니다. 설정에서 알림을 켜주세요.", Toast.LENGTH_LONG).show()
            setToggle(false)
        }
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
        const val ALARM_MINUTE_INTERVAL = 30L
        const val TAG_SETTING = "SETTING"
    }
}
