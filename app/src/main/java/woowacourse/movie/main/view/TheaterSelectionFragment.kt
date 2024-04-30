package woowacourse.movie.main.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterSelectionBinding
import woowacourse.movie.main.view.adapter.theater.TheaterAdapter
import woowacourse.movie.model.Theater
import java.time.LocalTime

class TheaterSelectionFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTheaterSelectionBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_theater_selection, container, false)
        val theaters = listOf(
            Theater("선릉", listOf(LocalTime.of(20, 0))),
            Theater("잠실", listOf(LocalTime.of(20, 0), LocalTime.of(20, 0))),
            Theater("강남", listOf(LocalTime.of(20, 0))),
        )
        binding.theaterRecyclerview.adapter = TheaterAdapter(theaters) {
            Log.e("TEST", "클릭 테스트~")
        }
        //        Intent(this, MovieDetailActivity::class.java).apply {
//            putExtra(KEY_MOVIE_ID, movidId)
//            startActivity(this)
//        }
        return binding.root
    }
}