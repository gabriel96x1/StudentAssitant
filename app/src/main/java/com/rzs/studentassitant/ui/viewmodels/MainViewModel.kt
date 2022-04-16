package com.rzs.studentassitant.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.rzs.studentassitant.model.MainMenuSectionModel

class MainViewModel : ViewModel() {

    val sectionList = mutableListOf<List<MainMenuSectionModel>>()


    fun getSections(query: String): List<MainMenuSectionModel> {

        val filteredList = linkedSetOf<MainMenuSectionModel>()

        sectionList.forEach { list: List<MainMenuSectionModel> ->

            list.forEach { mainMenuSectionModel ->

                if (mainMenuSectionModel.title.contains(query, ignoreCase = true)) {
                    filteredList.add(mainMenuSectionModel)
                }

                if (mainMenuSectionModel.description.contains(query, ignoreCase = true)) {
                    filteredList.add(mainMenuSectionModel)
                }

            }
        }

//        println("🤖 ViewModel Query: $query, filteredList: ${filteredList.size}")

        return filteredList.toList()
    }
}

