package com.capstone.miemo.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.miemo.R
import com.capstone.miemo.databinding.FragmentHistoryBinding
import com.capstone.miemo.ui.ViewModelFactory
import com.capstone.miemo.ui.home.HomeViewModel

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var memoAdapter: MemoAdapter
    //private lateinit var historyViewModel: HistoryViewModel

    private val historyViewModel: HistoryViewModel by viewModels {
        ViewModelFactory(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Move the RecyclerView initialization here
        val recyclerView: RecyclerView = root.findViewById(R.id.rv_memo)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize MemoAdapter with an empty list
        memoAdapter = MemoAdapter(emptyList())
        recyclerView.adapter = memoAdapter


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
