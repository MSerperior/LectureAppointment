package com.dsc.lectureAppointment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import androidx.core.app.ComponentActivity.ExtraData
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.provider.ContactsContract
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var inputNama : EditText
    private lateinit var inputAlamat : EditText
    private lateinit var submitButton : Button
    private lateinit var showText : TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputNama = findViewById(R.id.editText)
        inputAlamat = findViewById(R.id.editText2)
        submitButton = findViewById(R.id.button)
        showText = findViewById(R.id.textView)
        
        submitButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                save()
                showText.setText(inputNama.text)
            }

        })

    }

    private fun save(){
        val nama : String = inputNama.text.toString().trim()
        val alamat : String = inputAlamat.text.toString().trim()

        val ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("mahasiswa")

        val mhsId : String? = ref.push().key

        val mhs = mhsId?.let { Mahasiswa(it, nama, alamat) }

        if (mhsId != null) {
            ref.child(mhsId).setValue(mhs).addOnCompleteListener{
                Toast.makeText(applicationContext, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
