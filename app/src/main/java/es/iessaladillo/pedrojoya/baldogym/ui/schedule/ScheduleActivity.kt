package es.iessaladillo.pedrojoya.baldogym.ui.schedule

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.baldogym.R
import es.iessaladillo.pedrojoya.baldogym.data.LocalRepository
import es.iessaladillo.pedrojoya.baldogym.data.entity.TrainingSession
import es.iessaladillo.pedrojoya.baldogym.utils.invisibleUnless
import kotlinx.android.synthetic.main.schedule_activity.*

class ScheduleActivity : AppCompatActivity() {
    private val viewModel: ScheduleActivityViewModel by viewModels {
        ScheduleActivityViewModelFactory(LocalRepository, application)
    }
    private val listAdapter: ScheduleActivityAdapter = ScheduleActivityAdapter().also {
        it.onItemClickListener = { position ->
            changeJoin(position)

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.schedule_activity)
        setupViews()
        observeViewModelData()
    }

    private fun observeViewModelData() {
        observeTrainings()
    }

    private fun setupViews() {
        setupDays()
        setupRecyclerView()
        setupCurrentDay()

    }

    private fun setupCurrentDay() {

    }

    private fun setupDays() {
        lblDay1.text = getString(R.string.schedule_mon)
        lblDay2.text = getString(R.string.schedule_tue)
        lblDay3.text = getString(R.string.schedule_wed)
        lblDay4.text = getString(R.string.schedule_thu)
        lblDay5.text = getString(R.string.schedule_fri)
        lblDay6.text = getString(R.string.schedule_sat)
        lblDay7.text = getString(R.string.schedule_sun)

    }

    private fun setupRecyclerView() {
        lstTrainings.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            itemAnimator = DefaultItemAnimator()
            adapter = listAdapter
        }
    }
    private fun observeTrainings() {
        viewModel.trainings.observe(this) {
            showTrainings(it)
        }
    }
    private fun showTrainings(trainings: List<TrainingSession>) {
        lstTrainings.post {
            listAdapter.submitList(trainings)
            lblEmptyView.invisibleUnless(trainings.isEmpty())
        }
    }

    private fun changeJoin(position: Int) {
        val trainingSession = listAdapter.getItem(position)
        viewModel.updateTrainingJoinState(trainingSession, trainingSession.userJoined)


    }

}
