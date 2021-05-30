package com.example.dermate.ui.pickimage


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dermate.R
import com.example.dermate.data.models.ResultModel
import com.example.dermate.databinding.ActivityImagePickerBinding
import com.example.dermate.ml.ConvertedModel1
import com.example.dermate.ui.result.ResultActivity
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File


@Suppress("DEPRECATION")
class ImagePickerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImagePickerBinding
    private lateinit var data: ResultModel

    lateinit var bitmap: Bitmap
    var image_uri: Uri? = null


    companion object {
        const val FILE_MANAGER = 100
        const val OPEN_CAMERA = 101
        const val PERMISSION_CODE = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagePickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            openFileManager.setOnClickListener(clickListener)
            openCameraBtn.setOnClickListener(clickListener)
            startPredictionBtn.setOnClickListener(clickListener)
        }

    }

    @SuppressLint("QueryPermissionsNeeded")
    private val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.open_file_manager -> {
                Log.d("msg", "button pressed")
                val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(intent, FILE_MANAGER)
            }
            R.id.open_camera_btn -> {
                /*
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val photo = File(Environment.getExternalStorageDirectory(), "Pic.jpg")
                intent.putExtra(
                    MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(photo)
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED ||
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED
                    ) {
                        //permission was not enabled
                        val permission = arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                        //show popup to request permission
                        requestPermissions(permission, PERMISSION_CODE)
                    } else {
                        //permission already granted
                        openCamera()
                    }
                } else {
                    //system os is < marshmallow
                    openCamera()
                }

                 */
            }
            R.id.start_prediction_btn -> {
                val resizedImage = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
                val machineLearningModel = ConvertedModel1.newInstance(this)
                val tensorBuffer = TensorImage.fromBitmap(resizedImage)
                val byteBuffer = tensorBuffer.buffer

                // Creates inputs for reference.
                val inputFeature0 =
                    TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.UINT8)
                inputFeature0.loadBuffer(byteBuffer)

                // Runs model inference and gets result.
                val outputs = machineLearningModel.process(inputFeature0)
                val outputFeature0 = outputs.outputFeature0AsTensorBuffer
                val labelIndex = getMax(outputFeature0.floatArray)

                data = ResultModel(labelIndex, image_uri)
                machineLearningModel.close()

                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra(ResultActivity.DATA, data)
                startActivity(intent)

            }
        }
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, OPEN_CAMERA)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup was granted
                    openCamera()
                } else {
                    //permission from popup was denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == FILE_MANAGER) {
            if (resultCode == Activity.RESULT_OK) {
                image_uri = data?.data
                bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, image_uri)
                binding.apply {
                    imagePreview.setImageBitmap(bitmap)
                    startPredictionBtn.isEnabled = true
                }
            }
        } else if (requestCode == OPEN_CAMERA) {
            if (requestCode == Activity.RESULT_OK) {
                image_uri = data?.data
                bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, image_uri)
                binding.apply {
                    imagePreview.setImageBitmap(bitmap)
                    startPredictionBtn.isEnabled = true
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun getMax(arr: FloatArray): List<Int> {
        var ind = 0
        var ind1 = 0
        var ind2 = 0
        var min = 0.0f
        var max1 = 0.0f
        var max2 = 0.0f

        for (i in 0..10) {
            if (arr[i] > min) {
                min = arr[i]
                ind = i
                max1 = arr[i]
            }
        }
        min = 0.0f
        for (i in 0..10) {
            if (arr[i] > min && arr[i] < max1) {
                min = arr[i]
                ind1 = i
                max2 = arr[i]
            }
        }

        min = 0.0f
        for (i in 0..10) {
            if (arr[i] > min && arr[i] < max2) {
                min = arr[i]
                ind2 = i
            }
        }
        Toast.makeText(
            this,
            " grade index is $ind, $ind1, $ind2 from array {   ${arr[0]} ; ${arr[1]} ; ${arr[2]} ; ${arr[3]} ; ${arr[4]} ; ${arr[5]} ; ${arr[6]} ; ${arr[7]} ; ${arr[8]} ; ${arr[9]} ; ${arr[10]} }",
            Toast.LENGTH_LONG
        ).show()
        return listOf(ind, ind1, ind2)
    }
}