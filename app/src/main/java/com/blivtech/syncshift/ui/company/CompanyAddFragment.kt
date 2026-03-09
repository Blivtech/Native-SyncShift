package com.blivtech.syncshift.ui.company

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blivtech.syncshift.data.model.response.UiState
import com.blivtech.syncshift.databinding.FragmentAddCompanyBinding
import com.blivtech.syncshift.ui.components.ProgressDialog
import com.blivtech.syncshift.utils.CommonClass
import com.blivtech.syncshift.utils.SharedPreferencesManager
import dagger.hilt.android.AndroidEntryPoint
import javax.annotation.meta.When

@AndroidEntryPoint
class CompanyAddFragment :Fragment() {

    private var _binding: FragmentAddCompanyBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ShiftSelectionAdapter
    private val viewModel: CompanyViewModel by viewModels()
    private lateinit var progress: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCompanyBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observe()
        listener()
        viewModel.shiftList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)

        }
    }

    private fun listener() {
      binding.btnSubmit.setOnClickListener {
          saveCompanyDetails()
      }
    }

    private fun setupRecyclerView() {
        adapter = ShiftSelectionAdapter ()
        binding.rvShift.layoutManager = LinearLayoutManager(requireContext())
        binding.rvShift.adapter = adapter
    }


    private fun observe() {

        progress = ProgressDialog(requireContext())

        viewModel.saveResult.observe(viewLifecycleOwner) { state ->

            when (state) {

                is UiState.Loading -> {
                    progress.show(requireActivity().window)
                }

                is UiState.Error -> {
                    progress.dismiss(requireActivity().window)
                    showToast(state.message)
                }

                is UiState.Success -> {
                    progress.dismiss(requireActivity().window)

                    if (state.data) {
                        showToast(state.message)
                    } else {
                        showToast("Something went wrong")
                    }
                }
            }
        }
    }

    private fun saveCompanyDetails() {

        val btCode = SharedPreferencesManager
            .getLoginData(requireContext()).btCode

        val companyName = binding.etName.text.toString().trim()
        val companyType = binding.etIndustype.text.toString().trim()
        val cityName = binding.etTownName.text.toString().trim()

        val shiftDetails = adapter.getSelectedItems()

        when {
            companyName.isEmpty() ->
                showToast("Enter The Company Name")

            companyType.isEmpty() ->
                showToast("Enter The Company Type")

            cityName.isEmpty() ->
                showToast("Enter The City")

            shiftDetails.isEmpty() ->
                showToast("Select the Shifts")

            else -> {

                val data = CompanySaveRequestItem(
                    btCode = btCode,
                    companyName = companyName,
                    companyType = companyType,
                    townName = cityName,
                    companyCode = "",
                    townCode = "",
                    shiftDetails = shiftDetails
                )
                viewModel.saveCompany(data)
            }
        }
    }

    private  fun showToast(msg:String){
        CommonClass.showToast(requireContext(),msg)
    }
}


