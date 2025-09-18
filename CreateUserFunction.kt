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


override fun onMoreInfoClick(user: User) {
        val hiddenUserInfoLayout = binding.hiddenUserInfoLayout
        val hiddenUserInfoBody = binding.hiddenUserInfoBody
        val moreUserInfo = binding.moreUserInfo
        val userName = binding.userName
        val userEmail = binding.userEmail
        val userLineNumber = binding.userLineNumber
        val userRole = binding.userRole
        val userEnterprise = binding.userEnterprise
        val userAddress = binding.userAddress

        binding.hiddenUserInfoLayout.visibility = View.VISIBLE

        binding.userName.text = user.username
        binding.userEmail.text = user.email
        binding.userLineNumber.text = user.line_number.orNull()
        binding.userRole.text = user.role.name
        binding.userEnterprise.text = user.enterprise.name
        binding.userAddress.text = user.address.full_address.orNull()

        binding.hiddenUserInfoBody.setOnClickListener {
            binding.hiddenUserInfoLayout.visibility = View.GONE
        }
        binding.moreUserInfo.setOnClickListener{}
    } //Read

Log.d("API_EDIT_CLICK", "Usuário Original: $user")
        binding.hiddenEditUserLayout.visibility = View.VISIBLE
        binding.editUser.setOnClickListener{}

        binding.editUserName.setText(user.username)
        binding.editUserEmail.setText(user.email)
        binding.editUserLineNumber.setText(user.line_number)
        binding.editUserCEP.setText(user.address.zip_code)
        binding.editUserStreet.setText(user.address.street)
        binding.editUserNumber.setText(user.address.number)
        binding.editUserComplement.setText(user.address.complement)
        binding.editUserNeighborhood.setText(user.address.neighborhood)
        binding.editUserCity.setText(user.address.city)
        binding.editUserState.setText(user.address.state)
        binding.editUserCountry.setText(user.address.country)

        setupRoleSpinner(requireContext(), binding.editUserRole, allRolesCache!!, user.role.id)
        setupEnterpriseSpinner(requireContext(), binding.editUserEnterprise, allEnterprisesCache!!, user.enterprise.id)

        binding.editUserButton.setOnClickListener {
            if (binding.editUserPassword.text.toString() == binding.editUserPasswordCheck.text.toString()) { //validation
                val selectedRolePair = binding.editUserRole.getSelectedOrNull()
                val selectedRole = selectedRolePair?.let { UserRole(it.first, it.second) }

                val selectedEnterprisePair = binding.editUserEnterprise.getSelectedOrNull()
                val selectedEnterprise = selectedEnterprisePair?.let { UserEnterprise(it.first, it.second) }

                when {
                    binding.editUserName.text.isBlank() -> showError(requireContext(),"Nome não pode estar vazio")
                    selectedRole == null -> showError(requireContext(),"Cargo não pode estar vazio")
                    selectedEnterprise == null -> showError(requireContext(),"Empresa não pode estar vazia")
                    else -> {
                        val updatedUser = UserPayload(
                            username = binding.editUserName.text.toString(),
                            email = binding.editUserEmail.text.toString(),
                            password = binding.editUserPassword.text.toString(),
                            line_number = binding.editUserLineNumber.text.toString(),
                            enterprise_id = selectedEnterprise!!.id,
                            role_id = selectedRole!!.id,
                            role_label = selectedRole!!.name,
                            zip_code = binding.editUserCEP.text.toString(),
                            street = binding.editUserStreet.text.toString(),
                            number = binding.editUserNumber.text.toString(),
                            complement = binding.editUserComplement.text.toString(),
                            neighborhood = binding.editUserNeighborhood.text.toString(),
                            city = binding.editUserCity.text.toString(),
                            state = binding.editUserState.text.toString(),
                            country = binding.editUserCountry.text.toString()
                        )
                        editUser(requireContext(), user.id, updatedUser,
                            onSuccess = { updateFullList() }, onError = {})
                    }
                }
            } else { showError(requireContext(),"As senhas não concidem") }
        }
        binding.hiddenEditUserBody.setOnClickListener {
            binding.hiddenEditUserLayout.visibility = View.GONE
        }
        binding.cancelEditUserButton.setOnClickListener {
            binding.hiddenEditUserLayout.visibility = View.GONE
        }


val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Deletar Usuário")
            .setMessage("Você tem certeza que quer deletar esse usuário?")
            .setPositiveButton("Sim") { _, _ ->
                deleteUser(requireContext(), userId,
                    { updateFullList() }, {})
            }
            .setNegativeButton("Não", null)
            .create()

        dialog.setOnShowListener{
            dialog.window?.setBackgroundDrawableResource(android.R.color.white)
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(android.graphics.Color.RED)
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getColor(requireContext(), R.color.blue))
        }
        dialog.show()
