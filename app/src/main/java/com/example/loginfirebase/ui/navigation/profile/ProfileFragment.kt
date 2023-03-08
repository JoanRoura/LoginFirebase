package com.example.loginfirebase.ui.navigation.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.loginfirebase.R
import com.example.loginfirebase.databinding.FragmentProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding

    // creating a storage reference
     val storageRef = Firebase.storage.reference;

    private var currentFile: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val user = Firebase.auth.currentUser

        binding = DataBindingUtil.inflate<FragmentProfileBinding>(inflater, R.layout.fragment_profile, container, false);

        binding.buttonSelectImage.setOnClickListener {
            selectImage()
        }

        binding.buttonUploadImage.setOnClickListener {
            user?.let {
                uploadImageToStroage(it.uid)
            }
        }

        return binding.root
    }

    private fun selectImage() {
        Intent(Intent.ACTION_GET_CONTENT).also {
            it.type = "image/*"
            imageLauncher.launch(it)
        }
    }

    private val imageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                result.data?.data.let {
                    currentFile = it
                    binding.imageViewProfileImage .setImageURI(currentFile)
                }
            } else {
                Toast.makeText(
                    this.context,
                    "Fatal error carregant imatge!!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

    private fun uploadImageToStroage(filename: String) {
        try {
            currentFile?.let {
                //De moment pujo a la carpeta images del Storage Cloud
                storageRef.child("images/$filename").putFile(it)
                    .addOnSuccessListener {
                        Toast.makeText(this.context, "Arxiu pujat!!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this.context, "Error pujant l'arxiu", Toast.LENGTH_SHORT).show()
                    }
            }
        } catch (e: Exception) {
            Toast.makeText(this.context, "Error${e.toString()}", Toast.LENGTH_SHORT).show()
        }
    }


}