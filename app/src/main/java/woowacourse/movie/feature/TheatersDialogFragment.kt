package woowacourse.movie.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.domain.model.Screening
import woowacourse.movie.domain.model.Screenings

class TheatersDialogFragment(
    screenings: Screenings,
    navigateToBookingDetail: (Screening) -> Unit,
) : BottomSheetDialogFragment() {
    private val theaterAdapter: TheaterAdapter by lazy { TheaterAdapter(screenings, navigateToBookingDetail) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.dialog_fragment_theaters, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.rv_theaters).adapter = theaterAdapter
    }

    companion object {
        const val TAG = "SCREENS_DIALOG_FRAGMENT"
    }
}
