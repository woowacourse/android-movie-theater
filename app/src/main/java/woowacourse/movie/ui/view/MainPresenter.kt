package woowacourse.movie.ui.view

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {
    override fun presentScreen(mainScreen: MainScreen) {
        view.updateScreen(mainScreen)
    }
}
