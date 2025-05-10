package woowacourse.movie.view.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment : Fragment(R.layout.fragment_setting), SettingContract.View, SettingEventHandler {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: SettingContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter = SettingPresenter(this, SettingStorageManagerImpl(requireContext()))
        presenter.loadSettings()
        binding.handler = this
    }

    override fun showNotificationSetting(notificationEnabled: Boolean) {
        binding.notificationEnabled = notificationEnabled
    }

    override fun onNotificationSettingChanged() {
        presenter.toggleNotificationSetting()
    }
}
