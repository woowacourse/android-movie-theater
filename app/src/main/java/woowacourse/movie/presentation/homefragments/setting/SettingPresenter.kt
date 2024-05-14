package woowacourse.movie.presentation.homefragments.setting

class SettingPresenter(private val view: SettingContract.View) : SettingContract.Presenter {
    override fun loadNotificationState(isChecked: Boolean?) {
        isChecked?.let { view.setNotificationState(it) }
    }
}
