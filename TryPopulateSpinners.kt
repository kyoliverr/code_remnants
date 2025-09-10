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
