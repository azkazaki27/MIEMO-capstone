package com.capstone.miemo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.miemo.R
import com.capstone.miemo.databinding.FragmentHomeBinding
import androidx.navigation.fragment.findNavController

class  HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.btnAdd.setOnClickListener {
            showDialog()
        }
        binding.profileImage.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileActivity)
        }

        return root
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