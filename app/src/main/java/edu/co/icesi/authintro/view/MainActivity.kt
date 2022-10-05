package edu.co.icesi.authintro.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import edu.co.icesi.authintro.R
import edu.co.icesi.authintro.databinding.ActivityMainBinding
import edu.co.icesi.authintro.viewmodel.AuthResult
import edu.co.icesi.authintro.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    val viewmodel:MainViewModel by viewModels()

    val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewmodel.authState.observe(this){
            when(it.result){
                AuthResult.IDLE->{

                }
                AuthResult.SUCCESS->{
                    Toast
                        .makeText(this, "Registrado con exito", Toast.LENGTH_LONG)
                        .show()
                    startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
                }
                AuthResult.FAIL->{
                    Toast
                        .makeText(this, it.message, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

        binding.regBtn.setOnClickListener {
            regBtnAction()
        }
    }

    fun regBtnAction(){
        viewmodel.signUp(
            binding.nameET.text.toString(),
            binding.phoneET.text.toString(),
            binding.emailET.text.toString(),
            binding.passET.text.toString(),
        )
    }

}