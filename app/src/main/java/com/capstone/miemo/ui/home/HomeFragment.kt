package com.capstone.miemo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.capstone.miemo.R
import com.capstone.miemo.databinding.FragmentHomeBinding
import androidx.navigation.fragment.findNavController
import com.capstone.miemo.data.local.entity.Memo
import com.capstone.miemo.notification.DailyReminder
import com.capstone.miemo.ui.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.format

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

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        homeViewModel.getSession().observe(requireActivity()){ user ->
            binding.homeHeader.text = format(getString(R.string.home_header), user.name)
        }
        val currentDate = homeViewModel.getCurrentDate()
        homeViewModel.getMemoByDate(currentDate)?.observe(requireActivity()){ todayMemo ->
            updateUI(todayMemo)
        }
//        val todayMemo = lifecycleScope.launch{
//            homeViewModel.getTodayMemo(currentDate)
//        }
//        updateUI(todayMemo)

        binding.btnAdd.setOnClickListener {
            showDialog()
        }

        binding.profileImage.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileActivity)
        }

        return root
    }

    private fun updateUI(todayMemo: Memo?){
        if(todayMemo?.date != null){
            binding.btnAddLayout.visibility = View.GONE
            binding.todayMemo.visibility = View.VISIBLE
            DailyReminder().cancelAlarm(requireContext())

            binding.tvQuote.text = todayMemo.quote
            binding.tvMemo.text = todayMemo.memo
        }else{
            binding.btnAddLayout.visibility = View.VISIBLE
            binding.todayMemo.visibility = View.GONE
            DailyReminder().setDailyReminder(requireContext())

            binding.btnAdd.setOnClickListener {
                showDialog()
            }
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