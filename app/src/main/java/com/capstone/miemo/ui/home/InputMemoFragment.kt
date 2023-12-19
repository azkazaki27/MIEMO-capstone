package com.capstone.miemo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.capstone.miemo.R
import com.capstone.miemo.data.local.entity.Memo
import com.capstone.miemo.databinding.FragmentInputMemoBinding
import com.capstone.miemo.ui.ViewModelFactory

class InputMemoFragment: DialogFragment() {

    private var _binding: FragmentInputMemoBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentInputMemoBinding.inflate(inflater, container, false)
        val rootView: View = inflater.inflate(R.layout.fragment_input_memo, container, false)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_background)

        val memoText: EditText = binding.edtInput
        binding.btnSubmit.setOnClickListener {
            homeViewModel.insert(
                Memo(0, memoText.text.toString(), "")
            )
        }

        return rootView
    }
}