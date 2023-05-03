package woowacourse.movie.ui.fragment.setting

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import woowacourse.movie.DefaultPreference
import woowacourse.movie.R
import woowacourse.movie.ui.Toaster
import woowacourse.movie.ui.main.MainActivity.Companion.PERMISSIONS
import woowacourse.movie.util.hasPermissions

class SettingFragment : Fragment(R.layout.fragment_setting), SettingContract.View {
    private lateinit var presenter: SettingContract.Presenter

    private var switch: SwitchCompat? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = SettingPresenter(this, DefaultPreference(requireContext()))
        presenter.getBoolean(NOTIFICATIONS, false)

        switch = view.findViewById(R.id.notification_switch)
        switch?.setOnCheckedChangeListener { switchCompat, _ ->
            val permission = requireContext().hasPermissions(PERMISSIONS)
            val isChecked = when {
                permission -> switchCompat.isChecked
                else -> {
                    Toaster.showToast(requireContext(), "알림 권한을 허용해주세요.")
                    false
                }
            }
            presenter.setBoolean(NOTIFICATIONS, isChecked)
        }
    }

    override fun setSwitchChecked(boolean: Boolean) {
        switch?.isChecked = boolean
    }

    override fun onDestroyView() {
        super.onDestroyView()
        switch = null
    }

    companion object {
        const val NOTIFICATIONS = "notifications"
    }
}
