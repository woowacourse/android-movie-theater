package woowacourse.movie.ui.main.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.repository.SettingRepositoryImpl

class SettingFragment : Fragment(), SettingContract.View {
    override lateinit var presenter: SettingContract.Presenter

    private var _binding: FragmentSettingBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpPresenter()
    }

    private fun setUpPresenter() {
        SettingRepositoryImpl.init(requireContext())
        presenter = SettingPresenter(this, SettingRepositoryImpl)
    }

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

        setUpSwitchClick()
        setUpSwitchState()
    }

    private fun setUpSwitchClick() {
        binding.settingPushAlarmSwitch.setOnClickListener {
            presenter.setSwitchState(binding.settingPushAlarmSwitch.isChecked)
        }
    }

    private fun setUpSwitchState() {
        presenter.getNotificationState()
    }

    override fun updateSwitch(pushAlarmState: Boolean) {
        binding.settingPushAlarmSwitch.isChecked = pushAlarmState
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null

    }
}
