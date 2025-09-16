binding.hiddenCreateGroupLayout.visibility = View.VISIBLE

            setupEnterpriseSpinner(requireContext(), binding.newGroupEnterprise, allEnterprisesCache!!)

            binding.createGroup.setOnClickListener{}
            binding.hiddenCreateGroupBody.setOnClickListener {
                binding.hiddenCreateGroupLayout.visibility = View.GONE
            }
            binding.cancelNewGroupButton.setOnClickListener {
                binding.hiddenCreateGroupLayout.visibility = View.GONE
            }

            binding.createNewGroupButton.setOnClickListener {
                val groupName = binding.newGroupName.text.toString()
                val groupDescription = binding.newGroupDescription.text.toString()
                val groupEnterprise = binding.newGroupEnterprise.getSelectedOrNull()

                binding.createNewGroupButton.isClickable = false
                binding.loading.loaderScreen.visibility = View.VISIBLE

                when {
                    groupName.isEmpty() -> showError(requireContext(), "O nome do grupo é obrigatório")
                    groupDescription.isEmpty() -> showError(requireContext(), "A descrição do grupo é obrigatória")
                    groupEnterprise == null -> showError(requireContext(), "Selecione uma empresa")
                    else -> {
                        val newGroup = GroupPayLoad(
                            groupName, groupDescription, groupEnterprise!!.first
                        )
                        createGroup(requireContext(), newGroup,
                            onSuccess = {
                                binding.newGroupName.text?.clear()
                                binding.newGroupDescription.text?.clear()
                                binding.newGroupEnterprise.setSelection(0)

                                updateFullList()

                                binding.loading.loaderScreen.visibility = View.GONE
                                binding.hiddenCreateGroupLayout.visibility = View.GONE
                            }, onError = {
                                binding.loading.loaderScreen.visibility = View.GONE
                                binding.hiddenCreateGroupLayout.visibility = View.GONE
                            }
                        )
                    }
                }
            }
