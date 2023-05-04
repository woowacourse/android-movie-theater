package woowacourse.movie.view.moviemain.setting

import androidx.activity.result.ActivityResultLauncher
import woowacourse.movie.view.model.ReservationUiModel

interface SettingContract {
    interface View {
        var presenter: Presenter
        val requestPermissionLauncher: ActivityResultLauncher<String>
        fun setToggle(isOn: Boolean)
        fun cancelAlarms()
        fun setAlarms(reservations: List<ReservationUiModel>)
        fun requestNotificationPermission(): Boolean
    }

    interface Presenter {
        fun initToggle()
        fun onClick(isOn: Boolean)
    }
}
