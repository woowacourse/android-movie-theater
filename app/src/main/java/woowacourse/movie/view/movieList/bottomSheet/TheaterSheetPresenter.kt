package woowacourse.movie.view.movieList.bottomSheet

import woowacourse.movie.model.TheaterModel

class TheaterSheetPresenter(private val view: TheaterSheetContract.View) :
    TheaterSheetContract.Presenter {
    override fun setAdapter(theaters: List<TheaterModel>, moveToActivity: (TheaterModel) -> Unit) {
        val adapter = TheaterSheetAdapter(theaters, moveToActivity)
        view.setTheaterAdapter(adapter)
    }
}
