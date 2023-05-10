package woowacourse.movie.view.main

class MainPresenter(val view: MainContract.View) : MainContract.Presenter {
    override fun changeScreen(screen: Screen) {
        view.setScreen(screen)
    }
}
