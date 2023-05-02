package woowacourse.movie.view.moviemain.setting

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial
import woowacourse.movie.R

class SettingFragment : Fragment(R.layout.fragment_setting), SettingContract.View {

    private lateinit var toggle: SwitchMaterial
    override lateinit var presenter: SettingContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = SettingPresenter(this, requireContext())

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

    companion object {
        const val ALARM_MINUTE_INTERVAL = 30L
        const val TAG_SETTING = "SETTING"
    }
}
