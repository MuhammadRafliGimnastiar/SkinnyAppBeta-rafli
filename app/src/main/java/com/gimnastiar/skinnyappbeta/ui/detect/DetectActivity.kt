package com.gimnastiar.skinnyappbeta.ui.detect

import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.Window
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gimnastiar.skinnyappbeta.ui.ChoseImageButtomSheet
import com.gimnastiar.skinnyappbeta.ui.MainActivity
import com.gimnastiar.skinnyappbeta.R
import com.gimnastiar.skinnyappbeta.data.remote.model.Penanganan
import com.gimnastiar.skinnyappbeta.data.remote.model.ResponseUpToDb
import com.gimnastiar.skinnyappbeta.data.repository.Resource
import com.gimnastiar.skinnyappbeta.databinding.ActivityDetectBinding
import com.gimnastiar.skinnyappbeta.ui.ViewModelFactory
import com.gimnastiar.skinnyappbeta.utils.createCustomTempFile
import com.gimnastiar.skinnyappbeta.ui.dapter.HandlingAdapter
import com.gimnastiar.skinnyappbeta.utils.LoginPreference
import com.gimnastiar.skinnyappbeta.utils.imageMultipart
import com.gimnastiar.skinnyappbeta.utils.uriToFile
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.snackbar.Snackbar
import java.io.File

class DetectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetectBinding
    private lateinit var viewModel: DetectViewModel
    private var getFile: File? = null
    private lateinit var currentPhotoPath: String
    private lateinit var dialog: Dialog
    private lateinit var pref: LoginPreference
    private lateinit var username: String
    private lateinit var token: String

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val mFile = File(currentPhotoPath)
            getFile = mFile

            val imageBitmap = BitmapFactory.decodeFile(getFile?.path)
            binding.imgInput.setImageBitmap(imageBitmap)
            val drawable: Drawable? = binding.imgInput.drawable
            scanDialog(drawable)
            uploadImage()
        }
    }
    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val mFile = uriToFile(uri, this)
                getFile = mFile
                binding.imgInput.setImageURI(uri)
                val drawable: Drawable? = binding.imgInput.drawable
                scanDialog(drawable)
                uploadImage()

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val vmFactory = ViewModelFactory(this)
        viewModel = ViewModelProvider(this, vmFactory).get(DetectViewModel::class.java)

        pref = LoginPreference(this)
        val data = pref.getUserData()
        if (data.username != null) {
            username = data.username
            token = data.token!!
//            username = data.name.substring(1, data.name.length - 1)
            Log.i("TEST TOKEN", "username = $token")
        }

        val type = intent.getStringExtra(TAG)
        selectImageBy(type)

        binding.apply {
            imgInput.isVisible = false
            btnRetake.isVisible = false
            btnMedicine.isVisible = false
            tvTitleDeases.isVisible = false
            tvDeasesDescription.isVisible = false
            tvHandling.isVisible = false
            rvListHandlingDeases.isVisible = false
            btnSave.isVisible = false
        }

        imageClick()
        buttonClick()
    }

    private fun uploadImage() {
        getFile?.let { getFile -> imageMultipart(getFile) }?.let { fileMultipart ->
            viewModel.predictLiveResponse(fileMultipart, token).observe(this) { result ->
                if (result != null) {
                    when(result) {
                        is Resource.Loading -> {
                            showView(false)
                        }
                        is Resource.Error -> {
                            errorHanler(result.data)
                            Log.i("Predict Test", "EERROORRR ${result.data}")
                        }
                        is Resource.Success -> {
                            showView(true)
                                successHandler(result.data.penanganan)

                        }
                    }
                }
            }
//            observeData()
        }

    }

    private fun errorHanler(error: String) {
        with(binding) {
            animateError.isVisible = true
            tvError.text = error
            tvError.isVisible = true
            btnErrorBack.isVisible = true
            btnErrorBack.setOnClickListener {
                onBackPressed()
            }
        }
        showView(false)
        dialog.dismiss()
    }

    private fun successHandler(data: Penanganan){
        Log.i("Predict Test", "data success ${data.terdeteksiJenis}")
        val adapter = HandlingAdapter(data.judulPenanganan!!, data.penangananYangDapatDilakukan!!)
        binding.apply{
            tvTitleDeases.text = data.terdeteksiJenis
            tvDeasesDescription.text = data.deskripsi
            rvListHandlingDeases.layoutManager = LinearLayoutManager(this@DetectActivity)
            rvListHandlingDeases.adapter = adapter

            btnSave.setOnClickListener {
                binding.btnSave.setIconResource(R.drawable.baseline_bookmark_24)
                binding.btnSave.isEnabled = false
                addToHistory(data)
            }
        }

    }

    private fun addToHistory(data: Penanganan) {
        getFile?.let { getFile -> imageMultipart(getFile) }?.let { fileMultipart ->
            viewModel.addToHistory(
                username,
                fileMultipart,
                data.terdeteksiJenis!!,
                data.classTerprediksi!!
            ).observe(this@DetectActivity) {
                if (it != null) {
                    when(it) {
                        is Resource.Loading -> {
//                            showView(false)
                        }
                        is Resource.Error -> {
                            upToDbHandler(it.data, true)
                        }
                        is Resource.Success -> {
                            showView(true)
                            upToDbHandler(it.data.msg, it.data.error)
                        }
                    }
                }

            }
        }
    }

    private fun upToDbHandler(message: String, error: Boolean) {
        if (error) {
            Toast.makeText(this, "Gagal meniyimpan, coba lagi!", Toast.LENGTH_SHORT).show()
            binding.btnSave.setIconResource(R.drawable.baseline_bookmark_border_24)
            binding.btnSave.isEnabled = true
        } else{
            Toast.makeText(this, "Success save to bookmark!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun buttonClick(){
        binding.btnRetake.setOnClickListener{
            showChooseImageDialog()
        }
        binding.btnMedicine.setOnClickListener {
            Toast.makeText(this, "Belom jadi boss, sabar yaa!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showChooseImageDialog() {
        val dialog = ChoseImageButtomSheet()
        dialog.show(supportFragmentManager, ChoseImageButtomSheet.TAG)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun imageClick() {
        var height: Int = 0
        with(binding) {
            imgInput.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    // Mendapatkan nilai tinggi aktual
                    height = imgInput.height

                    // Menghapus listener agar tidak dipanggil lagi
                    imgInput.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    // Gunakan nilai tinggi sesuai kebutuhan Anda
                    Log.d("Image Height", "Tinggi gambar: $height piksel")
                }
            })

        }
        binding.imgInput.setOnClickListener{
            if (height == 550) {
                Log.d("Image Height", "IF Tinggi gambar: $height piksel")
                val layoutParams = binding.imgInput.layoutParams
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                binding.imgInput.layoutParams = layoutParams
                height = 0
            } else if (height == 0){
                val layoutParams = binding.imgInput.layoutParams
                layoutParams.height = 550
                binding.imgInput.layoutParams = layoutParams
                height = 550
            }
        }
    }

    private fun scanDialog(drawable: Drawable?) {
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_loading_scan)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        var imgInput: ShapeableImageView = dialog.findViewById(R.id.img_input_scan)
        if (drawable != null) {
            imgInput.setImageDrawable(drawable)
        } else {
            imgInput.setImageResource(R.drawable.banner_satu)
        }


        dialog.show()
    }


    private fun showView(data: Boolean) {
        binding.apply {
            imgInput.isVisible = data
            btnRetake.isVisible = data
            btnMedicine.isVisible = data
            tvTitleDeases.isVisible = data
            tvDeasesDescription.isVisible = data
            tvHandling.isVisible = data
            rvListHandlingDeases.isVisible = data
            btnSave.isVisible = data
        }
        if (data) {
            dialog.dismiss()
        } else {
            dialog.show()
        }
    }

    private fun selectImageBy(type: String?) {
        if (type != null){
            if (type.equals(CAMERA)) {
                startTakePhoto()
            } else if (type.equals(GALLERY)) {
                startGallery()
            }
        } else {
            Log.e("Predict Test", "string param is null!!")
        }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(this.packageManager)

        createCustomTempFile(this).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "com.gimnastiar.skinnyappbeta.mycamera",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }


    companion object{
        const val TAG = "ActivityDetect"
        const val CAMERA = "camera"
        const val GALLERY = "gallery"
        const val MILISECON = 5000.0
    }
}