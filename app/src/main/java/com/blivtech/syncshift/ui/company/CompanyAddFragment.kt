package com.blivtech.syncshift.ui.company

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blivtech.syncshift.databinding.FragmentAddCompanyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompanyAddFragment :Fragment() {

    private var _binding: FragmentAddCompanyBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ShiftSelectionAdapter
    private val viewModel: CompanyViewModel by viewModels()


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
        viewModel.shiftList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)

        }
    }

    private fun setupRecyclerView() {
        adapter = ShiftSelectionAdapter ()
        binding.rvShift.layoutManager = LinearLayoutManager(requireContext())
        binding.rvShift.adapter = adapter
    }

}


