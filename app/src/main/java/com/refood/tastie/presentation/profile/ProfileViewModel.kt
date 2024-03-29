package com.refood.tastie.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.refood.tastie.data.model.Profile

class ProfileViewModel : ViewModel() {

    val profileData = MutableLiveData(
        Profile(
            username = "rendialamsyah12",
            email = "rendirendi@gmail.com",
            noTelp = "08987675272",
            profileImg = "https://o-cdn-cas.sirclocdn.com/parenting/images/Spongebob.width-800.format-webp.webp"
        )
    )

    val isEditMode = MutableLiveData(false)

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }
}