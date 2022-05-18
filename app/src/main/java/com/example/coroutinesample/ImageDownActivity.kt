package com.example.coroutinesample

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.coroutinesample.databinding.ActivityImageDownBinding
import com.example.coroutinesample.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

//탑레벨 함수
suspend fun loadImage(imgUrl : String) : Bitmap {
    val url = URL(imgUrl)
    val stream = url.openStream()
    return BitmapFactory.decodeStream(stream)
}

class ImageDownActivity : AppCompatActivity() {
    private val binding by lazy { ActivityImageDownBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.run {
            buttonDownload.setOnClickListener {
                if(editTextTextPersonName.length() > 0){
                    CoroutineScope(Dispatchers.Main).launch {
                        progressBar.visibility = View.VISIBLE
                        val url = editTextTextPersonName.text.toString()
                        val bitmap = withContext(Dispatchers.IO){
                            loadImage(url)
                        }
                        DownImage.setImageBitmap(bitmap)
                        DownImage.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    }
                }else{
                    Toast.makeText(this@ImageDownActivity, "입력값이 없어요", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}