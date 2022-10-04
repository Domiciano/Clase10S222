package edu.co.icesi.authintro.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class MainViewModel : ViewModel(){

    private val _authState = MutableLiveData(0)
    val authState : LiveData<Int> get() = _authState

    //Accion de registro
    fun signUp(email:String, pass:String){
        viewModelScope.launch (Dispatchers.IO){
            try {
                val result = Firebase.auth.createUserWithEmailAndPassword(
                    email,
                    pass)
                    .await()
                Log.e(">>>", Firebase.auth.currentUser!!.uid)
                _authState.value = 1
            }catch (ex:Exception){
                Log.e(">>>", ex.localizedMessage)
                _authState.value = -1
            }
        }

    }

}
