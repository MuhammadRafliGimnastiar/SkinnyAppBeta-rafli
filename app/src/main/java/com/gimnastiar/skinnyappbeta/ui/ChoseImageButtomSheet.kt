package com.gimnastiar.skinnyappbeta.ui

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.gimnastiar.skinnyappbeta.R
import com.gimnastiar.skinnyappbeta.ui.detect.DetectActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChoseImageButtomSheet : BottomSheetDialogFragment() {

    private lateinit var timer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottom_sheet_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mCamera: TextView =  view.findViewById(R.id.tv_camera)
        val mGallery: TextView = view.findViewById(R.id.tv_gallery)

        mCamera.setOnClickListener{
            showWarningDiaolog(CAMERA)
            Toast.makeText(requireContext(), "Anda memilih Camera!", Toast.LENGTH_SHORT).show()
//            dismiss()
        }
        mGallery.setOnClickListener{
            showWarningDiaolog(GALLERY)
            Toast.makeText(requireContext(), "Anda memilih Gallery!", Toast.LENGTH_SHORT).show()
//            dismiss()
        }
    }

    private fun showWarningDiaolog(string: String) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_warning_ml)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        val btn_next: Button = dialog.findViewById(R.id.btn_next)

        timer = object : CountDownTimer(15_000, 100) {

            override fun onTick(remaining: Long) {
                btn_next.text = "Lanjutkan dalam ${remaining/1000}"
            }

            override fun onFinish() {
                timer.cancel()
                moveActivity(dialog, string)
            }
        }.start()


        btn_next.setOnClickListener{
            timer.cancel()
            moveActivity(dialog, string)
        }
    }

    private fun moveActivity(dialog: Dialog, string: String) {
        val intent = Intent(requireContext(), DetectActivity::class.java)
        intent.putExtra(DetectActivity.TAG, string)
        startActivity(intent)
        dialog.dismiss()
    }

    companion object {
        const val TAG = "ChoseImageBottomSheet"
        const val CAMERA = "camera"
        const val GALLERY = "gallery"
    }
}