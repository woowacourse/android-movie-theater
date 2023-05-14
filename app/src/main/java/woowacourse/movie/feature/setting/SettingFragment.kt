package woowacourse.movie.feature.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.domain.usecase.LoadAlarmSettingInfoUseCase
import com.example.domain.usecase.SetAlarmSettingUseCase
import woowacourse.movie.R
import woowacourse.movie.data.AlarmSettingRepositoryImpl
import woowacourse.movie.data.preference.AlarmSettingPreference
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.feature.common.Toaster
import woowacourse.movie.feature.main.MainActivity.Companion.PERMISSIONS
import woowacourse.movie.util.hasPermissions

class SettingFragment : Fragment(), SettingContract.View {
    private var _binding: FragmentSettingBinding? = null
    private val binding: FragmentSettingBinding
        get() = _binding!!

    private lateinit var presenter: SettingContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val alarmSettingRepositoryImpl = AlarmSettingRepositoryImpl(AlarmSettingPreference)
        presenter = SettingPresenter(
            this,
            LoadAlarmSettingInfoUseCase(alarmSettingRepositoryImpl),
            SetAlarmSettingUseCase(alarmSettingRepositoryImpl)
        )
        presenter.loadAlarmSettingInfo()
        binding.notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            val isPermission = requireActivity().hasPermissions(PERMISSIONS)
            presenter.changeSwitchCheckedState(isPermission, isChecked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setInitAlarmSettingInfo(initAlarmSetInfo: Boolean) {
        binding.notificationSwitch.isChecked = initAlarmSetInfo
    }

    override fun alarmPermissionIsNotApprove() {
        binding.notificationSwitch.isChecked = false
        Toaster.showToast(
            requireContext(),
            getString(R.string.alarm_premission_request_message)
        )
    }

    override fun errorLoadAlarmInfo() {
        Toaster.showToast(requireContext(), getString(R.string.error_load_alarm_setting_info))
        binding.notificationSwitch.isEnabled = false
    }

    override fun errorSetAlarmInfo() {
        Toaster.showToast(requireContext(), getString(R.string.error_set_alarm_setting_info))
    }
}
