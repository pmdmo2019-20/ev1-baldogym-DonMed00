package es.iessaladillo.pedrojoya.baldogym.ui.trainingsession

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import es.iessaladillo.pedrojoya.baldogym.R
import es.iessaladillo.pedrojoya.baldogym.data.LocalRepository
import es.iessaladillo.pedrojoya.baldogym.data.entity.TrainingSession
import es.iessaladillo.pedrojoya.baldogym.ui.schedule.ScheduleActivityViewModel
import es.iessaladillo.pedrojoya.baldogym.ui.schedule.ScheduleActivityViewModelFactory
import kotlinx.android.synthetic.main.schedule_activity_item.*
import kotlinx.android.synthetic.main.training_session_activity.*

class TrainingSessionActivity : AppCompatActivity() {
    private var id: Long = -1


    private val viewModel: ScheduleActivityViewModel by viewModels {
        ScheduleActivityViewModelFactory(LocalRepository, application)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.training_session_activity)
        getIntentData()
        setupViews()
    }



    private fun setupViews() {
        val training : TrainingSession =viewModel.getElement(id)
        btnJoinAct.setOnClickListener { changeJoin(training) }
        imgTraining.setImageResource(training.photoResId)
        lblTitleAct.text =training.name
        lblTimeAct.text = training.time
        lblRoomAct.text = training.room
        lblTrainerAct.text = training.trainer
        lblDay.text = training.weekDay.toString()
        lblDescription.text = training.description
        if(training.userJoined){
            btnJoinAct.background = resources.getDrawable(R.color.white_semi)
            btnJoinAct.setTextColor(resources.getColor(R.color.black))
            btnJoinAct.text=getString(R.string.schedule_item_quit)
        }else{
            btnJoinAct.background = resources.getDrawable(R.color.black)
            btnJoinAct.setTextColor(resources.getColor(R.color.white))
            btnJoinAct.text=getString(R.string.schedule_item_join)

        }
        lblBar.text=viewModel.element.value?.participants.toString().plus(" participants")


    }

    private fun changeJoin(training: TrainingSession) {
        viewModel.updateTrainingJoinState(training, training.userJoined)


    }

    private fun getIntentData() {
        if (intent == null || !intent.hasExtra(EXTRA_ID)) {
            throw RuntimeException(
                "TrainingSessionActivity needs to receive id"
            )
        }
        id = intent.getLongExtra(EXTRA_ID, -1)
    }

    companion object {
        const val EXTRA_ID = "EXTRA_ID"
        fun newIntent(context: Context, id: Long): Intent =
            Intent(context, TrainingSessionActivity::class.java)
                .putExtras(bundleOf(EXTRA_ID to id))
    }

}
