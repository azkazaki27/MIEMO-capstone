package com.capstone.miemo.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.capstone.miemo.R

class PasswordInputView : AppCompatEditText {
    private val minimumCharInput: Int = 8
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)
    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val totalCharCount = start + count
                if (totalCharCount < minimumCharInput)
                    error = context.getString(R.string.ed_login_password_error)
                else
                    error = null
            }
            override fun afterTextChanged(s: Editable?)
            { }
        })
    }
}