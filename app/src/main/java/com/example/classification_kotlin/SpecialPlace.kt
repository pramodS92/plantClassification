package com.example.classification_kotlin

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.classification_kotlin.ml.Model
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import android.app.Activity
import android.widget.*
import com.example.classification_kotlin.ml.MobilenetV110224Quant

class SpecialPlace : AppCompatActivity() {

    lateinit var bitmap: Bitmap
    lateinit var imgview: ImageView
    lateinit var openGalleryButton: RelativeLayout
    lateinit var predictButton: RelativeLayout
    lateinit var openCameraButton: RelativeLayout
    lateinit var predictTextView: TextView

    lateinit var imageClasssification: ImageClassification

    val fileName = "labels.txt"
    private val CAMERA_REQUEST_CODE = 42
    private val GALLERY_REQUEST_CODE = 43

    var plantList: Array<String> = emptyArray()
    val permissions = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_special_place)

        imageClasssification = ImageClassification()
        setUpUI()



        imgview.setOnClickListener {
            imgview.animate().apply {
                duration = 1000
                rotationYBy(360f)
            }.withEndAction{
                imgview.animate().apply {
                    duration = 1000
                    rotationYBy(360f)
                }.start()
            }
        }
    }

    /*
    Initiate UI properties
     */
    fun setUpUI(){
        imgview = findViewById(R.id.imageView)
        predictTextView = findViewById(R.id.predict_text)
        predictButton = findViewById(R.id.predict_btn)
        openGalleryButton = findViewById(R.id.open_gallery_btn)
        openCameraButton = findViewById(R.id.open_camera_btn)

        loadPlantList()
    }

    /*

     */
    fun loadPlantList(){
        val inputString = application.assets.open(fileName).bufferedReader().use { it.readText() }
        plantList = inputString.split("\n").toTypedArray()

        openImageFromGallery()
        captureImageFromCamera()
        predictImageClassification()
    }


    /*
    Predict selected image type
     */
    fun predictImageClassification() {
        predictButton.setOnClickListener(View.OnClickListener {

            imageClasssification.predictImageClassification(this)
//
//            var resized: Bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
//            val model = Model.newInstance(this)
//            val modelDefault = MobilenetV110224Quant.newInstance(this)
//
//            // Creates inputs for reference.
//            val inputFeature0 =
//                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.UINT8)
//
//            var tbuffer = TensorImage.fromBitmap(resized)
//            var byteBuffer = tbuffer.buffer
//
//            inputFeature0.loadBuffer(byteBuffer)
//
//            // Runs model inference and gets result.
//            val outputs = model.process(inputFeature0)
//            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
//
//            var max = getMax(outputFeature0.floatArray)
//
//            predictTextView.setText(plantList[max])
//
//            // Releases model resources if no longer used.
//            model.close()
        })
    }

    /*
    Open camera
     */
    fun captureImageFromCamera(){
        openCameraButton.setOnClickListener(View.OnClickListener {

            if(hasNoPermissions()){
                requestPermission()
            }else{
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if(takePictureIntent.resolveActivity(this.packageManager) != null){
                    startActivityForResult(takePictureIntent,CAMERA_REQUEST_CODE)
                }else{
                    Toast.makeText(this,"Unable to open camera",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }




    /*
    Open images
     */
    fun openImageFromGallery(){
        openGalleryButton.setOnClickListener(View.OnClickListener {
            var intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, GALLERY_REQUEST_CODE)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                val takenImage = data?.extras?.get("data") as Bitmap
                imgview.setImageBitmap(takenImage)
                bitmap = takenImage

            }else if (requestCode == GALLERY_REQUEST_CODE){
                imgview.setImageURI(data?.data)
                var uri: Uri? = data?.data
                bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
            }

        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


    fun getMax(arr: FloatArray): Int {
        var ind = 0
        var min = 0.0f

        for (i in 0..4) {
            if (arr[i] > min) {
                ind = i
                min = arr[i]
            }
        }

        return ind
    }



    private fun hasNoPermissions(): Boolean{
        return ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(){
        ActivityCompat.requestPermissions(this, permissions,0)
    }

}
