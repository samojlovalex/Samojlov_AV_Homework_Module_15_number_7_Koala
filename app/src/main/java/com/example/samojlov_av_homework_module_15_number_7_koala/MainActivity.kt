package com.example.samojlov_av_homework_module_15_number_7_koala

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.samojlov_av_homework_module_15_number_7_koala.databinding.ActivityMainBinding
import com.example.samojlov_av_homework_module_15_number_7_koala.utils.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var imageDogIV: ImageView
    private lateinit var buttonBT: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
    }

    private fun init() {

        toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        title = getString(R.string.toolbar_title)
        toolbar.subtitle = getString(R.string.toolbar_subtitle)

        imageDogIV = binding.imageDogIV
        buttonBT = binding.buttonBT

        buttonBT.setOnClickListener {
            getImageDog()
        }

    }

    private fun getImageDog() {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }
        GlobalScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val response =
                try {
                    RetrofitInstance.api.getRandomDog()
                } catch (e: IOException) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.app_error_Toast, e.message), Toast.LENGTH_LONG
                    ).show()
                    return@launch
                } catch (e: HttpException) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.http_error_Toast, e.message), Toast.LENGTH_LONG
                    )
                        .show()
                    return@launch
                }
            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    val data = response.body()
                    val image = data?.url
                    Picasso.get()
                        .load(image)
                        .into(imageDogIV)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    @SuppressLint("SetTextI18n", "ShowToast")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitMenu -> {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.toast_exit),
                    Toast.LENGTH_LONG
                ).show()
                finishAffinity()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}