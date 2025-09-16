package com.example.buscalog.function.group

import android.content.Context
import android.view.View
import com.example.buscalog.data.Group
import com.example.buscalog.databinding.FragmentGroupsBinding
import com.example.buscalog.repository.GroupsRepository.getAllGroups

fun groupList(
    context: Context,
    binding: FragmentGroupsBinding,
): ArrayList<Group> {
    var response = ArrayList<Group>()

    binding.groupRecycle.loader.visibility = View.VISIBLE
    binding.groupRecycle.recyclerView.visibility = View.GONE

    getAllGroups(context,
        onSuccess = { groups ->
            response = groups as ArrayList<Group>

            binding.groupRecycle.loader.visibility = View.GONE
            binding.groupRecycle.recyclerView.visibility = View.VISIBLE
        },onError = {
            binding.groupRecycle.loader.visibility = View.GONE
            binding.groupRecycle.recyclerView.visibility = View.VISIBLE })
    return response
}
