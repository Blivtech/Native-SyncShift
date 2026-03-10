package com.blivtech.syncshift.ui.company

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blivtech.syncshift.databinding.ItemCompanyBinding
import android.widget.PopupMenu
import com.blivtech.syncshift.R

class ActiveCompanyAdapter(
    private val onCompanyClick: (CompanyEntity) -> Unit
) : ListAdapter<CompanyEntity, ActiveCompanyAdapter.CompanyViewHolder>(CompanyDiffCallback()) {

    inner class CompanyViewHolder(
        private val binding: ItemCompanyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(company: CompanyEntity) {
            val name = company.companyName
            val industry = company.companyType

            binding.txtCompanyName.text = name
            binding.txtIndustry.text = industry

            binding.root.setOnClickListener {
                onCompanyClick(company)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {

        val binding = ItemCompanyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CompanyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

/**
 * DiffUtil for better RecyclerView performance
 */
class CompanyDiffCallback : DiffUtil.ItemCallback<CompanyEntity>() {

    override fun areItemsTheSame(
        oldItem: CompanyEntity,
        newItem: CompanyEntity
    ): Boolean {
        return oldItem.companyCode == newItem.companyCode
    }

    override fun areContentsTheSame(
        oldItem: CompanyEntity,
        newItem: CompanyEntity
    ): Boolean {
        return oldItem == newItem
    }
}