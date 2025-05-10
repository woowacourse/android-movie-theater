package woowacourse.movie.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.SharedPreferencesProvider
import woowacourse.movie.databinding.FragmentSettingBinding

class SettingFragment : Fragment(), SettingContract.View {
    private lateinit var presenter: SettingPresenter
    private lateinit var binding: FragmentSettingBinding
    private lateinit var preferencesProvider: SharedPreferencesProvider

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        preferencesProvider = SharedPreferencesProvider(requireContext())
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        presenter = SettingPresenter(this, preferencesProvider)

        showAlarmState()

        binding.switchAlarm.setOnCheckedChangeListener { _, isChecked ->
            presenter.setNotificationAlarm(isChecked)
        }
    }

    override fun showAlarmState() {
        val saved = preferencesProvider.isAlarmEnabled()
        binding.switchAlarm.isChecked = saved
    }
}
