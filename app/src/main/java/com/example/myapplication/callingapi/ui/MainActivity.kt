package com.example.myapplication.callingapi.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.callingapi.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val loginViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
        loginViewModel.fetchPermissionResponse() // Trigger the API call
    }

    private fun observeViewModel() {
        // Observe responseCode
        loginViewModel.responseCode.observe(this) { response ->
            response?.let {
                Toast.makeText(this, "Response Code: $it", Toast.LENGTH_SHORT).show()
            }
        }
        loginViewModel.permissionResponse.observe(this) {  response ->
            response?.let {
                binding.txtView.text = it[0].id.toString()
//                Toast.makeText(this, "Response: ${it[0].title}", Toast.LENGTH_SHORT).show()
            }

        }

        // Observe error messages
        loginViewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, "Error: $it", Toast.LENGTH_SHORT).show()
                loginViewModel.clearError()
            }
        }

        // Observe loading state
        loginViewModel.loading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}
