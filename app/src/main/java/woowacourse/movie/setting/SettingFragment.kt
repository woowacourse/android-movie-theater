package woowacourse.movie.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.main.permission.MoviePermissionHandler
import woowacourse.movie.main.sharedPreference.SharedPreferencesProvider

class SettingFragment : Fragment(), SettingContract.View {
    private lateinit var presenter: SettingPresenter
    private lateinit var binding: FragmentSettingBinding
    private lateinit var permissionHandler: MoviePermissionHandler
    private lateinit var preferencesProvider: SharedPreferencesProvider

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        permissionHandler = MoviePermissionHandler(requireContext())
        preferencesProvider = SharedPreferencesProvider(requireContext())
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        presenter = SettingPresenter(this, permissionHandler, preferencesProvider)

        initialSet()
        showAlarmState()

        binding.switchAlarm.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && !permissionHandler.hasAllPermission()) {
                presenter.setNotificationAlarm(false)
                binding.switchAlarm.isChecked = false
            } else {
                presenter.setNotificationAlarm(isChecked)
            }
        }
    }

    override fun showAlarmState() {
        val saved = preferencesProvider.isAlarmEnabled()
        binding.switchAlarm.isChecked = saved
    }

    private fun initialSet() {
        if (!preferencesProvider.isNotificationSet() && permissionHandler.hasAllPermission()) {
            presenter.setNotificationAlarm(true)
        }
    }
}
