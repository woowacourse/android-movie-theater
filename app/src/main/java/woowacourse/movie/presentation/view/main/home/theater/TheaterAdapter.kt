package woowacourse.movie.presentation.view.main.home.theater

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.presentation.model.Theater

class TheaterAdapter(
    private val theaters: List<Theater>, private val movieSchedule: List<List<String>>,
    private val clickEvent: (Theater, List<String>) -> Unit

) :
    RecyclerView.Adapter<TheaterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheaterViewHolder {
        Log.d("TheaterData", theaters.size.toString())
        return TheaterViewHolder(parent) {
            clickEvent(theaters[it], movieSchedule[it])
        }
    }

    override fun getItemCount(): Int = theaters.size

    override fun onBindViewHolder(holder: TheaterViewHolder, position: Int) {
        holder.bind(theaters[position], movieSchedule[position])
    }

}