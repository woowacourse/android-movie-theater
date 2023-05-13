package woowacourse.movie.view.activities.home.fragments.screeninglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.view.activities.screeningdetail.ScreeningDetailActivity

class ScreeningListFragment : Fragment(), ScreeningListContract.View {

    private val presenter: ScreeningListContract.Presenter = ScreeningListPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_screening_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadScreenings()
    }

    override fun setScreeningList(screeningListViewItemUIStates: List<ScreeningListViewItemUIState>) {
        val screeningListView = view?.findViewById<RecyclerView>(R.id.screening_list_view) ?: return
        screeningListView.adapter = ScreeningListAdapter(screeningListViewItemUIStates) { screeningId ->
            ScreeningDetailActivity.startActivity(screeningListView.context, screeningId)
        }
    }
}
