package woowacourse.movie.feature.setting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.MovieApplication
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.feature.setting.contract.SettingContract
import woowacourse.movie.feature.setting.presenter.SettingPresenter

class SettingFragment :
    Fragment(),
    SettingContract.View {
    private lateinit var binding: FragmentSettingBinding
    private val presenter: SettingContract.Presenter by lazy {
        SettingPresenter(
            this,
            (requireActivity().application as MovieApplication).settingRepository,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadNotificationSetting()
        binding.presenter = presenter
    }

    override fun updateNotificationSettingSwitch(isNotificationEnabled: Boolean) {
        binding.isNotificationEnabled = isNotificationEnabled
    }
}
