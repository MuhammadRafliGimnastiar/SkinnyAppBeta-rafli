package com.gimnastiar.skinnyappbeta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.w3c.dom.Text

class ChoseImageButtomSheet : BottomSheetDialogFragment() {

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
            Toast.makeText(requireContext(), "Anda memilih Camera!", Toast.LENGTH_SHORT).show()
        }
        mGallery.setOnClickListener{
            Toast.makeText(requireContext(), "Anda memilih Gallery!", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val TAG = "ChoseImageBottomSheet"
    }
}