package es.iessaladillo.pedrojoya.baldogym.ui.schedule

import android.os.Bundle
import android.widget.TextView
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
import es.iessaladillo.pedrojoya.baldogym.data.entity.WeekDay
import es.iessaladillo.pedrojoya.baldogym.data.entity.getCurrentWeekDay
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
        val listTextView: ArrayList<TextView> = arrayListOf(lblDay1,lblDay2,lblDay3,lblDay4,lblDay5,lblDay6,lblDay7)

        listTextView.forEach {
            it.setOnClickListener { selectDay(it as TextView) }
        }
    }

    private fun selectDay(lblDay: TextView) {
        when(lblDay){
            lblDay1 -> setDay(getString(R.string.schedule_monday))
            lblDay2 -> setDay(getString(R.string.schedule_tuesday))
            lblDay3 -> setDay(getString(R.string.schedule_wednesday))
            lblDay4 -> setDay(getString(R.string.schedule_thursday))
            lblDay5 -> setDay(getString(R.string.schedule_friday))
            lblDay6 -> setDay(getString(R.string.schedule_saturday))
            lblDay7 -> setDay(getString(R.string.schedule_sunday))

        }
        val listTextView: ArrayList<TextView> = arrayListOf(lblDay1,lblDay2,lblDay3,lblDay4,lblDay5,lblDay6,lblDay7)

        listTextView.forEach {
            it.setTextColor(resources.getColor(R.color.white_semi))
            }

    }

    private fun changeColor(lblDay: TextView) {

    }


    private fun setDay(day: String) {
        lblCurrentDay.text = day
        when(day){
            getString(R.string.schedule_monday) -> viewModel.filterAllTrainingsOfDay(WeekDay.MONDAY)
            getString(R.string.schedule_tuesday) -> viewModel.filterAllTrainingsOfDay(WeekDay.TUESDAY)
            getString(R.string.schedule_wednesday) -> viewModel.filterAllTrainingsOfDay(WeekDay.WEDNESDAY)
            getString(R.string.schedule_thursday) -> viewModel.filterAllTrainingsOfDay(WeekDay.THURSDAY)
            getString(R.string.schedule_friday) -> viewModel.filterAllTrainingsOfDay(WeekDay.FRIDAY)
            getString(R.string.schedule_saturday) -> viewModel.filterAllTrainingsOfDay(WeekDay.SATURDAY)
            getString(R.string.schedule_sunday) -> viewModel.filterAllTrainingsOfDay(WeekDay.SUNDAY)

        }
    }

    private fun setupDays() {
        lblDay1.text = getString(R.string.schedule_mon)
        lblDay2.text = getString(R.string.schedule_tue)
        lblDay3.text = getString(R.string.schedule_wed)
        lblDay4.text = getString(R.string.schedule_thu)
        lblDay5.text = getString(R.string.schedule_fri)
        lblDay6.text = getString(R.string.schedule_sat)
        lblDay7.text = getString(R.string.schedule_sun)
        lblCurrentDay.text = getCurrentWeekDay().toString()

    }

    private fun setupRecyclerView() {
        lstTrainings.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            itemAnimator = DefaultItemAnimator()
            adapter = listAdapter
        }

        viewModel.filterAllTrainingsOfDay(getCurrentWeekDay())
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
