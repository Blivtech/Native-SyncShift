package com.blivtech.syncshift.ui.home.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blivtech.syncshift.databinding.FragmentHomeBinding
import com.blivtech.syncshift.ui.attendance.AttendanceActivity
import com.blivtech.syncshift.ui.company.CompanyActivity
import com.blivtech.syncshift.utils.CommonClass
import com.blivtech.syncshift.utils.SharedPreferencesManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var shiftAdapter: ShiftAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        function()
        binding.ivComArrow.setOnClickListener{
            CommonClass.launchActivity(requireContext(),CompanyActivity::class.java)
        }

    }


    override fun onResume() {
        super.onResume()
        setCompanyHead()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setCompanyHead(){
        val name =SharedPreferencesManager.getActiveCompanyName(requireContext())
        val type =SharedPreferencesManager.getActiveCompanyIndustryName(requireContext())

        binding.txtCompany.text=name
        binding.txtRole.text=type

    }

    private fun function(){
        setCompanyHead()
        setRecyclerView()
        observe()
    }

   private fun setRecyclerView(){
        shiftAdapter = ShiftAdapter(){

           val bundle=Bundle()
            bundle.putString("shiftCode",it.shiftCode)
            bundle.putString("shiftName",it.shiftName)
            bundle.putBoolean("isEdit",false)
            CommonClass.launchActivity(requireContext(),bundle,AttendanceActivity::class.java)
         }

        binding.rvAttendance.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter = shiftAdapter
        }

    }

    private fun observe(){

        homeViewModel.shiftList.observe(viewLifecycleOwner) { shiftList ->
            shiftAdapter.submitList(shiftList)
        }
    }
}
