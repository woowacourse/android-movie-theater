package woowacourse.movie.ui.setting

class MovieSettingPresenter(
    private val view: MovieSettingContract.View,
) :
    MovieSettingContract.Presenter {
    override fun loadNotificationStatus() {
        view.showNotificationStatus()
    }
}
