package es.iessaladillo.pedrojoya.baldogym.data

import es.iessaladillo.pedrojoya.baldogym.data.entity.TrainingSession
import es.iessaladillo.pedrojoya.baldogym.data.entity.WeekDay

interface Repository {



    fun queryAllTrainingsOfDay(weekDay: WeekDay): List<TrainingSession>

    // Marca como asistida en la fuente de datos la actividad cuyo id
    // corresponde con el recibido..
    fun markTrainingAssist(taskId: Long)

    // Marca como no asistida la tarea cuyo id corresponde con
    // el recibido.
    fun markTrainingNoAssist(taskId: Long)

}