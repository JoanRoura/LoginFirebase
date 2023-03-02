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


        // Obtenim les dades del email i el password del usuari, del activity Login
        val bundleSignIn: Bundle? = intent.extras
        val email: String? = bundleSignIn?.getString("email")
        val password: String? = bundleSignIn?.getString("password")

        binding.editTextEmail.setText(email)
        binding.editTextPassword.setText(password   )

        setup()
        session()
    }

    override fun onStart() {
        super.onStart()

        binding.authLayout.visibility = View.VISIBLE
    }

    private fun session() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val password = prefs.getString("password", null)

        if (email != null && password != null) {
            binding.authLayout.visibility = View.INVISIBLE
            goToHome(email, password)
        }
    }

    private fun setup() {

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
                                goToHome( login.result?.user?.email ?: "", password.toString())

                                Toast.makeText(this,"Bienvenido",Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            showAlert("Ha ocurrido un error al iniciar session.")
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

    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("Acceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun goToHome(email: String, password: String) {
        val intentHomeActivity = Intent(this, MainActivity::class.java).apply {
            putExtra("email", email)
            putExtra("password", password)
        }
        startActivity(intentHomeActivity)
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
                                goToHome(account.email ?: "", "******");
                            } else {
                                showAlert("No se ha podido iniciar session con tu cuenta de Google")
                            }
                        }
                }

            } catch (e: ApiException) {
                showAlert("Ha ocurrido un erroe al iniciar session con Google")
            }
        }

    }
}