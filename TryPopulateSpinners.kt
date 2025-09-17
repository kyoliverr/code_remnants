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
    

