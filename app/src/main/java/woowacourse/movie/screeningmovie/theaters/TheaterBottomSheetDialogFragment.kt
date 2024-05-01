package woowacourse.movie.screeningmovie.theaters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.moviereservation.MovieReservationActivity
import woowacourse.movie.moviereservation.MovieReservationContract

class TheaterBottomSheetDialogFragment : BottomSheetDialogFragment(),AdapterClickListener {

    private lateinit var rcv: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.bottom_sheet_theater, container, false)
        rcv = view.findViewById(R.id.rcv_theater)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcv.adapter = TheaterAdapter(
            listOf(Theater(0L,"선릉",""),Theater(0L,"선릉","")),
            this
        )

    }

    override fun onClick(id: Long) {
        startActivity(MovieReservationActivity.getIntent(requireContext(), 0))
    }
}
