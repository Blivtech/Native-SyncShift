package com.blivtech.syncshift.ui.home.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.blivtech.syncshift.R
import com.blivtech.syncshift.databinding.FragmentHomeBinding
import com.blivtech.syncshift.ui.addEmployee.AddEmployee
import com.blivtech.syncshift.ui.login.LoginActivity
import com.blivtech.syncshift.utils.CommonClass
import com.blivtech.syncshift.utils.SharedPreferencesManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeFragmentViewModel by viewModels()
    private lateinit var menuAdapter: MenuAdapter

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

        // Setup RecyclerView
        binding.rvList.layoutManager = GridLayoutManager(requireContext(), 3)
        menuAdapter = MenuAdapter { menuItem ->
            when (menuItem.title) {
                getString(R.string.menu_logout) -> {
                    CommonClass.launchActivity(requireContext(), LoginActivity::class.java)
                    SharedPreferencesManager.setLoginStatus(requireContext(), false)
                    requireActivity().finish()
                }
                getString(R.string.menu_add_employee) -> {
                    CommonClass.launchActivity(requireContext(), AddEmployee::class.java)
                }
                else -> {
                    CommonClass.showToast(requireContext(), "Under Development")
                }
            }
        }

        binding.rvList.adapter = menuAdapter

        // Observe menu items
        viewModel.items.observe(viewLifecycleOwner) { menuList ->
            menuAdapter.submitList(menuList)
        }

        // Load menu items
        viewModel.loadMenuItems()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
