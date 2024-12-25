
package com.example.myapplication.callingapi.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.callingapi.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private lateinit var binding: ActivityMainBinding
    private val loginViewModel: MainActivityViewModel by viewModels()
//    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setupRxObservers()
//        loginViewModel.fetchPermissionResponse() // Trigger the API call

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
                recyclerView = binding.recyclerView
                binding.recyclerView.layoutManager = LinearLayoutManager(this)

                // Initialize the adapter with a click listener
                recyclerView.adapter = PostAdapter(it) { post ->
                    // Show a toast with the title of the clicked item
                    Toast.makeText(this, "Clicked: ${post.title}", Toast.LENGTH_SHORT).show()
                }
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

    override fun onDestroy() {
        super.onDestroy()
        loginViewModel.clearPermissionResponse()
    }

//    private fun setupRxObservers() {
//        // Subscribe to permissionResponse
//        disposables.add(
//            loginViewModel.permissionResponseObservable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                    { response ->
//                        // Update the UI with the response
//                        Timber.d("Fetching data for userId: ${response[2].title}")
//                        binding.txtView.text = response[0].id.toString()
//                    },
//                    { throwable ->
//                        // Handle errors
//                        Toast.makeText(this, "Error: ${throwable.message}", Toast.LENGTH_SHORT).show()
//                    }
//                )
//        )
//
//        // Subscribe to loading state
//        disposables.add(
//            loginViewModel.loadingObservable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                    { isLoading ->
//                        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//                    },
//                    { throwable ->
//                        // Handle unexpected errors
//                        Toast.makeText(this, "Error: ${throwable.message}", Toast.LENGTH_SHORT).show()
//                    }
//                )
//        )
//
//        // Subscribe to errors
//        disposables.add(
//            loginViewModel.errorObservable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                    { errorMessage ->
//                        Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
//                    },
//                    { throwable ->
//                        // Handle unexpected errors
//                        Toast.makeText(this, "Error: ${throwable.message}", Toast.LENGTH_SHORT).show()
//                    }
//                )
//        )
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        disposables.clear() // Clear all subscriptions to prevent memory leaks
//    }


}
