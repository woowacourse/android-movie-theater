package woowacourse.movie.presentation.view.main.booklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.domain.Reservation
import com.example.domain.ReservationRepository
import woowacourse.movie.databinding.FragmentBookListBinding


class BookListFragment : Fragment() {
    private lateinit var binding: FragmentBookListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookListBinding.inflate(inflater, container, false)
        setRecyclerView(ReservationRepository.findAll())
        return binding.root
    }

    private fun setRecyclerView(reservations: List<Reservation>) {
        binding.rvMovieList.adapter = BookingListAdapter(reservations)
    }
}