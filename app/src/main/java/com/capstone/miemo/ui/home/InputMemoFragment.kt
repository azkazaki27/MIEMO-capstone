package com.capstone.miemo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.miemo.R
import com.capstone.miemo.data.local.entity.Memo
import com.capstone.miemo.databinding.FragmentInputMemoBinding

class InputMemoFragment: DialogFragment() {

    private var _binding: FragmentInputMemoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        val rootView: View = inflater.inflate(R.layout.fragment_input_memo, container, false)

        val memoText: EditText = binding.edtInput
        binding.btnSubmit.setOnClickListener {
            homeViewModel.insert(
                Memo(0, memoText.text.toString(), "")
            )
        }

        return rootView
    }
}