package com.refood.tastie.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.refood.tastie.data.repository.UserRepository
import com.refood.tastie.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {
    private val _registerResult = MutableLiveData<ResultWrapper<Boolean>>()
    val registerResult: LiveData<ResultWrapper<Boolean>>
        get() = _registerResult

    fun doRegister(
        fullName: String,
        email: String,
        password: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.doRegister(fullName, email, password).collect {
                _registerResult.postValue(it)
            }
        }
    }
}
