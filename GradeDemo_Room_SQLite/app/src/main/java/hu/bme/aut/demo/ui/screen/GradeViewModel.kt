package hu.bme.aut.demo.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.demo.data.Grade
import hu.bme.aut.demo.data.GradeDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class GradeViewModel(
    var gradeDAO: GradeDAO
) : ViewModel() {
    fun getAllGrades(): Flow<List<Grade>> {
        //return gradeDAO.getSpecialGrades("A+")
        return gradeDAO.getAllGrades()
    }

    fun deleteAllGrades() {
        viewModelScope.launch(Dispatchers.IO) {
            gradeDAO.deleteAllGrades()
        }
    }

    suspend fun addGrade(grade: Grade) {
        gradeDAO.insert(grade)
    }
}