package com.gimnastiar.skinnyappbeta.ui.customView

import android.content.Context
import android.graphics.Canvas
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import com.gimnastiar.skinnyappbeta.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MyInputEditText : TextInputEditText {

    private lateinit var textInputLayout: TextInputLayout

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context,attrs,defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        textInputLayout = parent.parent as TextInputLayout
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            val delay: Long = 1000
            val handler: Handler = Handler(Looper.getMainLooper())

            private val inputFinishChecker = Runnable {
                when (inputType) {
                    0x00000021 -> {
                        if (!Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()) {
                            textInputLayout.error = context.getString(R.string.user_name)
                        }
                    }
                    0x00000081 -> {
                        if (text.toString().length < 6) {
                            textInputLayout.error = context.getString(R.string.password_wrong)
                        }
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                textInputLayout.isErrorEnabled = false
                handler.removeCallbacks(inputFinishChecker)
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNotEmpty()) {
                    handler.postDelayed(inputFinishChecker, delay)
                }
            }

        })
    }
}