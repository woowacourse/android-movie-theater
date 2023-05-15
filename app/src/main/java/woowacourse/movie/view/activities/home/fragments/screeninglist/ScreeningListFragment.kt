package woowacourse.movie.view.activities.home.fragments.screeninglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import woowacourse.movie.R
import woowacourse.movie.repository.ScreeningRepository
import woowacourse.movie.view.activities.home.fragments.screeninglist.uistates.ScreeningListUIState
import woowacourse.movie.view.activities.home.fragments.screeninglist.uistates.TheaterUIState
import woowacourse.movie.view.activities.home.fragments.screeninglist.uistates.TheatersUIState
import woowacourse.movie.view.activities.screeningdetail.ScreeningDetailActivity

class ScreeningListFragment : Fragment(), ScreeningListContract.View {

    private val presenter: ScreeningListContract.Presenter =
        ScreeningListPresenter(this, ScreeningRepository)

    private lateinit var container: ViewGroup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.container = container ?: throw IllegalArgumentException("상영 목록 프래그먼트의 컨테이너가 널입니다.")
        return inflater.inflate(R.layout.fragment_screening_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadScreenings()
    }

    override fun setScreeningList(screeningListUIState: ScreeningListUIState) {
        val screeningListView = view?.findViewById<RecyclerView>(R.id.screening_list_view) ?: return
        screeningListView.adapter =
            ScreeningListAdapter(screeningListUIState.screenings) { screeningId ->
                presenter.onReserveNow(screeningId)
            }
    }

    override fun showTheaters(theaters: TheatersUIState) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetView =
            layoutInflater.inflate(R.layout.bottom_sheet_theater, container, false)
        theaters.theaters.forEach { theater ->
            val theaterItem =
                makeTheaterItem(theaters.screeningId, theater, bottomSheetView as ViewGroup)
            bottomSheetView.addView(theaterItem)
        }
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }

    private fun makeTheaterItem(
        screeningId: Long,
        theater: TheaterUIState,
        root: ViewGroup
    ): View? {
        val item = layoutInflater.inflate(R.layout.item_theater, root, false)
        val theaterNameView = item.findViewById<TextView>(R.id.tv_theater_name)
        theaterNameView.text = theater.theaterName
        val screeningTimeCount = item.findViewById<TextView>(R.id.tv_screening_time_count)
        screeningTimeCount.text = getString(R.string.screening_time_count).format(theater.screeningTimeCount)
        item.setOnClickListener {
            ScreeningDetailActivity.startActivity(requireContext(), screeningId, theater.theaterId)
        }
        return item
    }
}
