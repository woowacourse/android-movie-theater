package woowacourse.movie.activity.bookComplete

import com.woowacourse.domain.movie.MovieBookingSeatInfo
import woowacourse.movie.model.MovieBookingSeatInfoUIModel
import woowacourse.movie.model.toPresentation

class BookCompletePresenter(
    val view: BookCompleteContract.View,
    override val movieBookingSeatInfo: MovieBookingSeatInfo,
) : BookCompleteContract.Presenter {

    override fun initView() {
        view.initView(movieBookingSeatInfo.toPresentation())
    }

    override fun hasDummyData() {
        if (movieBookingSeatInfo.toPresentation() == MovieBookingSeatInfoUIModel.dummyData) {
            view.displayToastIfDummyData()
        }
    }
}
