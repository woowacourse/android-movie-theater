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
        presenter = SettingPresenter(SettingRepositoryImpl)
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
        updateSwitch()
    }

    override fun updateSwitch() {
        binding.settingPushAlarmSwitch.isChecked = presenter.getNotificationState()
    }

    private fun setUpSwitchClick() {
        binding.settingPushAlarmSwitch.setOnClickListener {
            presenter.setSwitchState(binding.settingPushAlarmSwitch.isChecked)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}
