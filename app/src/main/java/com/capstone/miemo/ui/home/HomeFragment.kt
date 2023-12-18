package com.capstone.miemo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.capstone.miemo.databinding.FragmentHomeBinding
import com.capstone.miemo.ui.ViewModelFactory
import com.capstone.miemo.ui.auth.AuthViewModel

class  HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //private lateinit var homeViewModel: HomeViewModel

    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        binding.btnAdd.setOnClickListener {
            showDialog()
        }

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
    }

    private fun showDialog() {
        val dialog = InputMemoFragment()

        dialog.show(childFragmentManager, "inputDialog")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}