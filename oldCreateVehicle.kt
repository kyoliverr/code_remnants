if (newLicensePlate.isNotEmpty() && newEnterprise != null && newType != null) {
                    if (newDescription.isEmpty() || newDescription.length < 50) {
                        val newVehicle = VehiclePayLoad(
                            newLicensePlate, newType.second, newDescription,
                            newModel, newManufacturer, newYear, newColor, newChassis,
                            newSecurityAsk, newSecurityAnswer,
                            newEnterprise.first, null, newFuel
                        )

                        Log.d("API_SENDING_VEHICLE", "Enviando para API: $newVehicle")
                        VehicleRepository.createVehicle(
                            requireContext(),
                            newVehicle,
                            onSuccess = {
                                binding.newVehicleLicensePlate.text?.clear()
                                binding.newVehicleManufacturer.text?.clear()
                                binding.newVehicleModel.text?.clear()
                                binding.newVehicleColor.text?.clear()
                                binding.newVehicleYear.text?.clear()
                                binding.newVehicleChassis.text?.clear()
                                binding.newVehicleFuel.text?.clear()
                                binding.newVehicleSecurityAsk.text?.clear()
                                binding.newVehicleSecurityAnswer.text?.clear()
                                binding.newVehicleDescription.text?.clear()
                                binding.newVehicleEnterprise.setSelection(0)
                                binding.newVehicleType.setSelection(0)

                                updateFullList()

                                binding.hiddenCreateVehicleLayout.visibility = View.GONE
                                binding.loading.loaderScreen.visibility = View.GONE
                                binding.createNewVehicleButton.isClickable = true
                            }, onError = {
                                binding.loading.loaderScreen.visibility = View.GONE
                                binding.createNewVehicleButton.isClickable = true
                            }
                        )
                    }
                    else {
                        Toast.makeText(requireContext(), "A descrição deve ter menos de 50 caracteres", Toast.LENGTH_SHORT).show()
                        binding.newVehicleDescription.setHintTextColor(resources.getColor(R.color.red))
                        binding.newVehicleDescription.setOnClickListener{ binding.newVehicleDescription.setHintTextColor(resources.getColor(R.color.textColorHint))}
                    }
                }
                else{
                    Toast.makeText(requireContext(), R.string.fillAllFields, Toast.LENGTH_SHORT).show()
                    if (newLicensePlate.isEmpty()){ binding.newVehicleLicensePlate.setHintTextColor(resources.getColor(R.color.red))}
                    binding.newVehicleLicensePlate.setOnClickListener{ binding.newVehicleLicensePlate.setHintTextColor(resources.getColor(R.color.textColorHint))}
                    binding.newVehicleEnterprise.validateSelection()
                }
