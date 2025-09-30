private fun tryPopulateCreateSpinners(vehicle: Vehicle? = null){
        if (binding.hiddenCreateVehicleLayout.visibility != View.VISIBLE) return

        SpinnerUi.setupEnterpriseSpinner(requireContext(), binding.newVehicleEnterprise, allEnterprisesCache!!, vehicle?.enterprise!!.id)
        SpinnerUi.setupTypeSpinner(requireContext(), binding.newVehicleType, vehicle?.type)
    }
    private fun tryPopulateEditSpinners(){
        if (binding.hiddenEditVehicleLayout.visibility != View.VISIBLE) return

        val allEnterprises = allEnterprisesCache
        val associatedEnterprise = mutableListOf(SpinnerItem(0, "Selecione a Empresa"))
        allEnterprises?.forEach { enterprise ->
            associatedEnterprise.add(SpinnerItem(enterprise.id, enterprise.name)) }
        binding.editVehicleEnterprise.adapter = customSpinnerAdapter(requireContext(), associatedEnterprise.distinctBy { it.id })
    }


        val allRoles = allRolesCache
        val associatedRoles = mutableListOf(SpinnerItem(0, "Cargo*"))
        allRoles?.forEach { role ->
            associatedRoles.add(SpinnerItem(role.id, role.name)) }
        binding.newUserRole.adapter = customSpinnerAdapter(requireContext(), associatedRoles.distinctBy { it.id })
        binding.newUserRole.setSelection(0)

        val allEnterprises = allEnterprisesCache
        val associatedEnterprises = mutableListOf(SpinnerItem(0, "Empresa Responsável"))
        allEnterprises?.forEach { enterprise ->
            associatedEnterprises.add(SpinnerItem(enterprise.id, enterprise.name)) }
        binding.newUserEnterprise.adapter = customSpinnerAdapter(requireContext(), associatedEnterprises.distinctBy { it.id })
        binding.newUserEnterprise.setSelection(0)

        val allRoles = allRolesCache
        val associatedRoles = mutableListOf(SpinnerItem(0, "Cargo*"))
        allRoles?.forEach { role ->
            associatedRoles.add(SpinnerItem(role.id, role.name)) }
        binding.editUserRole.adapter = customSpinnerAdapter(requireContext(), associatedRoles.distinctBy { it.id })

        val allEnterprises = allEnterprisesCache
        val associatedEnterprises = mutableListOf(SpinnerItem(0, "Empresa Responsável"))
        allEnterprises?.forEach { enterprise ->
            associatedEnterprises.add(SpinnerItem(enterprise.id, enterprise.name)) }
        binding.editUserEnterprise.adapter = customSpinnerAdapter(requireContext(), associatedEnterprises.distinctBy { it.id })

        private fun tryPopulateMainUserSpinners(enterprise: Enterprise) {
        if(binding.hiddenEnterpriseMainUserLayout.visibility != View.VISIBLE) return

        val allUsers = allUsersCache
        val associatedUsers = mutableListOf<SpinnerItem>()
        allUsers?.forEach { user ->
            val sameEnterprise = (user.enterprise.id == enterprise.id)
            val notMainUser = (user.id != enterprise.mainUser?.id)

            if (sameEnterprise && notMainUser){
                associatedUsers?.add(SpinnerItem(user.id, user.email ?: ""))
            }
        }
        if (associatedUsers.isEmpty()) {
            associatedUsers.add(SpinnerItem(0, "Nenhum usuário disponível"))
        }
        else { associatedUsers.add(0, SpinnerItem(0, "Selecione um usuário")) }

        Log.d("API_LINK_USERS", "Usuários: ${associatedUsers}") //Remove after
        binding.linkEnterpriseMainUser.adapter = customSpinnerAdapter(requireContext(), associatedUsers.distinctBy { it.id })
        binding.linkEnterpriseMainUser.setSelection(0)
    }
    

