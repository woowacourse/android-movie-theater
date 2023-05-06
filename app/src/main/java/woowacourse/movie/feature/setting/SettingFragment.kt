package woowacourse.movie.feature.setting

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.setting.MovieReminderSetting
import woowacourse.movie.feature.Toaster
import woowacourse.movie.util.hasPermission
import woowacourse.movie.util.requestPermission

class SettingFragment : Fragment(R.layout.fragment_setting), SettingContract.View {

    private val movieReminderSetting by lazy {
        MovieReminderSetting.getInstance().init(requireContext())
    }
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    private lateinit var presenter: SettingContract.Presenter
    private lateinit var notificationSwitch: SwitchCompat

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)

        notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            val hasPermission: Boolean = hasPermission(requireActivity(), Manifest.permission.POST_NOTIFICATIONS)
            presenter.changeMovieReminderChecked(hasPermission, isChecked)
        }
    }

    override fun onResume() {
        super.onResume()
        val hasPermission: Boolean = hasPermission(requireActivity(), Manifest.permission.POST_NOTIFICATIONS)
        presenter.setMovieReminderChecked(hasPermission)
    }

    override fun onDetach() {
        super.onDetach()
        movieReminderSetting.releaseInstance()
    }

    private fun init(view: View) {
        presenter = SettingPresenter(this, movieReminderSetting)
        notificationSwitch = view.findViewById(R.id.notification_switch)
    }

    override fun setMovieReminderChecked(value: Boolean) {
        notificationSwitch.isChecked = value
    }

    override fun requestPermission() {
        Toaster.showToast(requireContext(), "설정에서 알림 권한을 허용해주세요.")
        requestPermission(
            requireActivity(),
            Manifest.permission.POST_NOTIFICATIONS,
            requestPermissionLauncher::launch
        )
    }
}
