package es.iessaladillo.pedrojoya.baldogym.ui.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.baldogym.R
import es.iessaladillo.pedrojoya.baldogym.data.entity.TrainingSession
import kotlinx.android.extensions.LayoutContainer

class ScheduleActivityAdapter : RecyclerView.Adapter<ScheduleActivityAdapter.ViewHolder>() {

    private var data: List<TrainingSession> = emptyList()
    var onItemClickListener: ((Int) -> Unit)? = null

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return data[position].id
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.schedule_activity_item, parent, false)
        return ViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trainingSession: TrainingSession = data[position]
        holder.bind(trainingSession)
    }

    fun submitList(newList: List<TrainingSession>) {
        data = newList
        notifyDataSetChanged()
    }

    fun getItem(position: Int): TrainingSession = data[position]


    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            //seton click
        }

        fun bind(trainingSession: TrainingSession) {

        }
    }
}