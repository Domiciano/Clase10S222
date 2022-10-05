package edu.co.icesi.authintro.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import edu.co.icesi.authintro.R
import edu.co.icesi.authintro.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        if(Firebase.auth.currentUser == null){
            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
            finish()
            return
        }

        //Descargar un objeto
        getMyInfo()
    }

    fun getMyInfo(){
        lifecycleScope.launch(Dispatchers.IO){
            val result = Firebase.firestore
                .collection("users")
                .document(Firebase.auth.currentUser!!.uid)
                .get().await()
            val user = result.toObject(User::class.java)
            withContext(Dispatchers.Main){
                Toast.makeText(this@ProfileActivity,
                    "Bienvenido ${user?.name}",
                    Toast.LENGTH_LONG)
                    .show()
            }
        }
    }


}