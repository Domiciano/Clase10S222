package edu.co.icesi.authintro.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import edu.co.icesi.authintro.R
import edu.co.icesi.authintro.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    val viewmodel:MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewmodel.authState.observe(this){
            when(it){
                -1->{}
                0->{}
                1->{}
            }
        }
    }

    fun regBtnAction(){
        viewmodel.signUp("a@a.com", "123456")
    }

}