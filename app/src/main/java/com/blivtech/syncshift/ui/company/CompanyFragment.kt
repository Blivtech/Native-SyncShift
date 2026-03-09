package com.blivtech.syncshift.ui.company

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.blivtech.syncshift.R
import com.blivtech.syncshift.databinding.FragmentCompanyBinding
import com.blivtech.syncshift.utils.SharedPreferencesManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompanyFragment : Fragment() {

    private var _binding: FragmentCompanyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CompanyViewModel by viewModels()
    private lateinit var adapter: ActiveCompanyAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompanyBinding.inflate(inflater, container, false)

        setupRecyclerView()
        setCompanyHead()
        observe()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnJoin.setOnClickListener{
            findNavController().navigate(R.id.companyAddFragment)
        }

    }
    private fun setupRecyclerView() {

        adapter = ActiveCompanyAdapter { company ->
            val name = company.companyName
            val code = company.companyCode
            val industry = company.companyType

        }

        binding.rvCompanies.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCompanies.adapter = adapter
    }

    private fun setCompanyHead(){
        val name =SharedPreferencesManager.getActiveCompanyName(requireContext())
        val type =SharedPreferencesManager.getActiveCompanyIndustryName(requireContext())
        binding.txtCompanyName.text=name
        binding.txtIndustry.text=type

    }
    private fun observe() {
        val  companyCode=SharedPreferencesManager.getActiveCompanyCode(requireContext())
        viewModel.companyList.observe(viewLifecycleOwner) { companies ->
            val filtered=companies.filter { it.companyCode !=companyCode}
            adapter.submitList(filtered)
        }
    }

    }
