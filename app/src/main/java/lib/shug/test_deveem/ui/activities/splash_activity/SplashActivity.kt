package lib.shug.test_deveem.ui.activities.splash_activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import lib.shug.test_deveem.R
import lib.shug.test_deveem.databinding.ActivitySplashBinding
import lib.shug.test_deveem.ui.activities.main_activity.MainActivity
import lib.shug.test_deveem.model.utils.Resource
import lib.shug.test_deveem.utils.toProductEntity

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //UI Settings
        supportActionBar?.hide()
        window.navigationBarColor = Color.BLACK
        window.statusBarColor = getColor(R.color.grey_light)

        getAllProductsAndSaveLocal()
    }

    private fun getAllProductsAndSaveLocal() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getAllProducts().collect { response ->
                    when (response) {
                        is Resource.Error -> {
                            Log.e("shug", "products: error ${response.message}")
                        }

                        is Resource.Loading -> {
                            Log.e("shug", "products: loading")
                        }

                        is Resource.Success -> {
                            CoroutineScope(Dispatchers.IO).launch {
                                viewModel.deleteAll()
                                val insertJobs = response.data?.map { product ->
                                    async {
                                        viewModel.insertProductDB(product.toProductEntity())
                                    }
                                }
                                insertJobs?.awaitAll()
                                navigateToMainActivity()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}