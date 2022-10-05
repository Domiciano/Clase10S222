package edu.co.icesi.authintro.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.co.icesi.authintro.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainViewModel : ViewModel(){

    private val _authState = MutableLiveData(
        AuthState(AuthResult.IDLE, "Starting...")
    )
    val authState : LiveData<AuthState> get() = _authState

    //Accion de registro
    fun signUp(name:String, phone:String, email:String, pass:String){
        viewModelScope.launch (Dispatchers.IO){
            try {
                val result = Firebase.auth.createUserWithEmailAndPassword(
                    email,
                    pass)
                    .await()
                Log.e(">>>", Firebase.auth.currentUser!!.uid)
                //Registrar el objeto en Firestore
                val user = User(Firebase.auth.currentUser!!.uid, name, phone, email)
                Firebase.firestore.collection("users")
                    .document(user.id).set(user).await()



                withContext(Dispatchers.Main){ _authState.value = AuthState(AuthResult.SUCCESS, "Success") }

            }catch (ex:Exception){
                Log.e(">>>", ex.localizedMessage)
                withContext(Dispatchers.Main){ _authState.value = AuthState(AuthResult.FAIL, ex.localizedMessage)}
            }

        }
    }
}
data class AuthState(val result:AuthResult, val message:String)
enum class AuthResult{ IDLE, FAIL, SUCCESS }