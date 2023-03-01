package com.example.loginfirebase.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.loginfirebase.R
import com.example.loginfirebase.databinding.ActivityLoginBinding
import com.example.loginfirebase.model.User
import com.example.loginfirebase.ui.MainActivity
import com.example.loginfirebase.ui.ProviderType
import com.example.loginfirebase.ui.VerificationActivity
import com.example.loginfirebase.ui.signin.SignInActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var loginViewModel: LoginViewModel
    private val GOOGLE_SIGN_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        // Analytics Event
        val analytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integracio de Firebase completa ")
        analytics.logEvent("InitScreen", bundle)

        loginViewModel.emailUser.value

        getStrings()
        setup()
        session()
    }

    private fun getStrings() {

    }

    override fun onStart() {
        super.onStart()

        binding.authLayout.visibility = View.VISIBLE
    }

    private fun session() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val name = prefs.getString("username", null)
        val email = prefs.getString("email", null)
        val password = prefs.getString("password", null)
        val provider = prefs.getString("provider", null)

        if (name != null && email != null && password != null && provider != null) {
            binding.authLayout.visibility = View.INVISIBLE
            showVerification(email, password, ProviderType.valueOf(provider))
        }
    }

    private fun setup() {

        val db = Firebase.firestore

        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextEmail.text
            val password = binding.editTextPassword.text

            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener { login ->
                        if (login.isSuccessful) {
                            // Enviar correo de verificacio
                            login.result?.user?.sendEmailVerification();

                            // Comprovar que l'usuari a verificat el correo
                            if (login.result?.user?.isEmailVerified == true) {
//                                showVerification(
//                                    login.result?.user?.email ?: "",
//                                    password.toString(),
//                                    ProviderType.BASIC
//                                )
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(this,"Bienvenido",Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            showAlert()
                        }
                    }
            }
        }

        binding.buttonGoToRegister.setOnClickListener(){
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.buttonGoogle.setOnClickListener {

            // Configuaracio del inici de sessio amb google
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()

            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)

        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("No hi ha una conta registrada amb aquestes credencials.")
        builder.setPositiveButton("Acceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showVerification(email: String, password: String, provider: ProviderType) {
        val intentVerificationActivity = Intent(this, VerificationActivity::class.java).apply {
            putExtra("email", email)
            putExtra("password", password)
            putExtra("provider", provider.name)
        }
        startActivity(intentVerificationActivity)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)

                if (account != null) {

                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                showVerification(account.email ?: "", "******" ,
                                    ProviderType.GOOGLE
                                )
                            } else {
                                showAlert()
                            }
                        }
                }

            } catch (e: ApiException) {
                showAlert()
            }
        }

    }
}