package com.capstone.miemo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.capstone.miemo.R
import com.capstone.miemo.data.local.database.MemoRoomDatabase
import com.capstone.miemo.data.local.entity.Memo
import com.capstone.miemo.data.remote.response.SubmitRequest
import com.capstone.miemo.databinding.FragmentInputMemoBinding
import com.capstone.miemo.ui.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InputMemoFragment: DialogFragment() {

    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory(requireActivity())
    }

    private val memoCollection = Firebase.firestore.collection("texts")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val rootView: View = inflater.inflate(R.layout.fragment_input_memo, container, false)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_background)

        val btnSubmit: Button = rootView.findViewById(R.id.btn_submit)

        btnSubmit.setOnClickListener {
            val memoText: EditText = rootView.findViewById(R.id.edt_input)
            val userId = homeViewModel.userId.toString()
            val submitMemo = SubmitRequest(userId, memoText.toString())
            submitText(submitMemo)
            val quote = getText(userId)
            val date = homeViewModel.getCurrentDate()
            val memo = Memo(
                0,
                memoText.text.toString(),
                quote.toString(),
                date
            )
            saveMemo(memo)
        }

        return rootView
    }

    private fun submitText(memo: SubmitRequest) = CoroutineScope(Dispatchers.IO).launch {
        try {
            memoCollection.document(memo.userId).set({memo.text})
            withContext(Dispatchers.Main){
                Toast.makeText(requireActivity(), "Submit Memo Success", Toast.LENGTH_SHORT).show()
            }
        }catch (e: Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getText(userId: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            memoCollection.document(userId).get()
            withContext(Dispatchers.Main){
                Toast.makeText(requireActivity(), "Get Memo Success", Toast.LENGTH_SHORT).show()
            }
        }catch (e: Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveMemo(memo: Memo){
        CoroutineScope(Dispatchers.IO).launch {
            homeViewModel.insert(memo)
        }
        dismiss()
    }
}