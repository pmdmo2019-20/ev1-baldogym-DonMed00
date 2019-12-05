package es.iessaladillo.pedrojoya.baldogym.ui.schedule

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.baldogym.data.Repository
import es.iessaladillo.pedrojoya.baldogym.data.entity.TrainingSession
import es.iessaladillo.pedrojoya.baldogym.data.entity.WeekDay
import es.iessaladillo.pedrojoya.baldogym.data.entity.getCurrentWeekDay

class ScheduleActivityViewModel(
    private val repository: Repository,
    private val application: Application
) : ViewModel() {

    private val _trainings: MutableLiveData<List<TrainingSession>> = MutableLiveData()
    val trainings: LiveData<List<TrainingSession>>
        get() = _trainings

     val _currentDay: MutableLiveData<WeekDay> =
        MutableLiveData(getCurrentWeekDay())

    private val _element : MutableLiveData<TrainingSession> = MutableLiveData()
    val element : LiveData<TrainingSession>
    get() = _element

    init {
        refreshLists(repository.queryAllTrainingsOfDay(_currentDay.value!!))

    }

    private fun refreshLists(newList: List<TrainingSession>) {
        _trainings.value = newList.sortedBy { it.time }
    }

    fun filterAllTrainingsOfDay(weekDay: WeekDay){
        queryTrainings(weekDay)
    }

    fun updateTrainingJoinState(trainingSession: TrainingSession, userJoined: Boolean) {
        if (!userJoined) {
            repository.markTrainingAssist(trainingSession.id)
        } else {
            repository.markTrainingNoAssist(trainingSession.id)
        }
        queryTrainings(_currentDay.value!!)
        getElement(trainingSession.id)
    }


    private fun queryTrainings(weekDay: WeekDay) {
        refreshLists(repository.queryAllTrainingsOfDay(weekDay))
        _currentDay.value = weekDay
    }

     fun getElement(id: Long): TrainingSession {
         _element.value = repository.getElement(id)
        return element.value!!
    }



}