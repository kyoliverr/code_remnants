binding.hiddenCreateUserLayout.visibility = View.VISIBLE

            setupRoleSpinner(requireContext(), binding.newUserRole, allRolesCache!!)
            setupEnterpriseSpinner(requireContext(), binding.newUserEnterprise, allEnterprisesCache!!)

            binding.hiddenCreateUserBody.setOnClickListener {
                binding.hiddenCreateUserLayout.visibility = View.GONE
            }
            binding.cancelNewUserButton.setOnClickListener {
                binding.hiddenCreateUserLayout.visibility = View.GONE
            }
            binding.createNewUserButton.setOnClickListener {
                val newUserName = binding.newUserName.text.toString()
                val newUserEmail = binding.newUserEmail.text.toString()
                val newUserPassword = binding.newUserPassword.text.toString()
                val newUserPasswordCheck = binding.newUserPasswordCheck.text.toString()
                val newUserLineNumber = binding.newUserLineNumber.text.toString()
                val newUserRole = binding.newUserRole.getSelectedOrNull()
                val newUserEnterprise = binding.newUserEnterprise.getSelectedOrNull()
                val newUserZip = binding.newUserCEP.text.toString()
                val newUserStreet = binding.newUserStreet.text.toString()
                val newUserNumber = binding.newUserNumber.text.toString()
                val newUserComplement = binding.newUserComplement.text.toString()
                val newUserNeighborhood = binding.newUserNeighborhood.text.toString()
                val newUserCity = binding.newUserCity.text.toString()
                val newUserState = binding.newUserState.text.toString()
                val newUserCountry = binding.newUserCountry.text.toString()

                when {
                    newUserName.isBlank() -> showError(requireContext(),"Nome é obrigatório")
                    newUserEmail.isBlank() -> showError(requireContext(),"E-mail é obrigatório")
                    newUserPassword.isBlank() -> showError(requireContext(),"Senha é obrigatória")
                    newUserPasswordCheck.isBlank() -> showError(requireContext(),"Confirme a senha")
                    newUserRole == null -> showError(requireContext(),"Cargo é obrigatório")
                    newUserEnterprise == null -> showError(requireContext(),"Empresa é obrigatória")
                    newUserPassword != newUserPasswordCheck -> showError(requireContext(),"As senhas não concidem")
                    else -> {
                        if (validatePassword(requireContext(), newUserPassword)){
                            val newUser = UserPayload(
                                newUserName, newUserEmail, newUserPassword,
                                newUserLineNumber, false, newUserEnterprise.first,
                                null, newUserRole.first, newUserRole.second,
                                newUserZip, newUserStreet, newUserNumber, newUserComplement,
                                newUserNeighborhood, newUserState, newUserCountry,
                                newUserCity
                            )
                            createUser(requireContext(), newUser,
                                onSuccess = {
                                    binding.newUserName.text?.clear()
                                    binding.newUserEmail.text?.clear()
                                    binding.newUserPassword.text?.clear()
                                    binding.newUserPasswordCheck.text?.clear()
                                    binding.newUserLineNumber.text?.clear()
                                    binding.newUserRole.setSelection(0)
                                    binding.newUserEnterprise.setSelection(0)
                                    binding.newUserCEP.text?.clear()
                                    binding.newUserStreet.text?.clear()
                                    binding.newUserNumber.text?.clear()
                                    binding.newUserComplement.text?.clear()
                                    binding.newUserNeighborhood.text?.clear()
                                    binding.newUserCity.text?.clear()
                                    binding.newUserState.text?.clear()
                                    binding.newUserCountry.text?.clear()

                                    updateFullList()
                                    binding.hiddenCreateUserLayout.visibility = View.GONE }, onError = {})
                        }
                    }
                }
            }
            binding.createUser.setOnClickListener{}
