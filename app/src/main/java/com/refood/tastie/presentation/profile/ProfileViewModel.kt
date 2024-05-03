package com.refood.tastie.presentation.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.refood.tastie.data.repository.UserRepository
import com.refood.tastie.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val repo: UserRepository) : ViewModel() {

    val isEditMode = MutableLiveData(false)
    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }

    private val _changePhotoResult = MutableLiveData<ResultWrapper<Boolean>>()
    val changePhotoResult: LiveData<ResultWrapper<Boolean>>
        get() = _changePhotoResult

    private val _changeProfileResult = MutableLiveData<ResultWrapper<Boolean>>()
    val changeProfileResult: LiveData<ResultWrapper<Boolean>>
        get() = _changeProfileResult

    fun getCurrentUser() = repo.getCurrentUser()

    fun updateProfilePicture(photoUri: Uri?) {
        viewModelScope.launch(Dispatchers.IO) {
            photoUri?.let {
                repo.updateProfile(photoUri = photoUri).collect {
                    _changePhotoResult.postValue(it)
                }
            }
        }
    }

    fun updateFullName(fullName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateProfile(fullName = fullName).collect {
                _changeProfileResult.postValue(it)
            }
        }
    }

    fun createChangePwdRequest() {
        repo.sendChangePasswordRequestByEmail()
    }

    fun doLogout() {
        repo.doLogout()
    }
}