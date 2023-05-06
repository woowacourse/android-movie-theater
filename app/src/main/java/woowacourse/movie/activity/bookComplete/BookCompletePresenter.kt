package woowacourse.movie.activity.bookComplete

import com.woowacourse.domain.movie.MovieBookingSeatInfo
import woowacourse.movie.movie.MovieBookingSeatInfoUIModel
import woowacourse.movie.movie.toPresentation

class BookCompletePresenter(
    val view: BookCompleteContract.View,
    override val movieBookingSeatInfo: MovieBookingSeatInfo,
) : BookCompleteContract.Presenter {

    init {
        view.initView(movieBookingSeatInfo.toPresentation())
    }

    override fun hasDummyData() {
        if (movieBookingSeatInfo.toPresentation() == MovieBookingSeatInfoUIModel.dummyData) {
            view.displayToastIfDummyData()
        }
    }
}
