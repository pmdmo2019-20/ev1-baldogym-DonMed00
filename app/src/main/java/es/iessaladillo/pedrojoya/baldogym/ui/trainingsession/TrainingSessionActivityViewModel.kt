package es.iessaladillo.pedrojoya.baldogym.ui.trainingsession

import android.app.Application
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.baldogym.data.Repository

class TrainingSessionActivityViewModel(
    private val repository: Repository,
    private val application: Application
) : ViewModel() {
}