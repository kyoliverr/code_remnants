binding.insideRecyclerView.visibility = View.GONE
        binding.insideLoader.visibility = View.VISIBLE
        binding.loading.loaderScreen.visibility = View.VISIBLE
        binding.linkVehicleButton.isEnabled = false
        val permission = "vehicle.linkDevice"
        val api = RetrofitInstance.getApiService(requireContext(), permission)
        lifecycleScope.launch {
            try{
                val response = api.linkDeviceToVehicle(vehicle.id, device)//device
                if (response.isSuccessful) {
                    Log.d("API_SUCCESS", "Status: ${response.body()?.status}, Message: ${response.body()?.text}, Dispositivo $device vinculado a veículo ${vehicle.id}")
                    Toast.makeText(requireContext(), "Dispositivo vinculado", Toast.LENGTH_SHORT).show()

                    val newDevice = VehicleDevices(
                        id = device.device,
                        imei = allDevicesCache?.find { it.id == device.device }?.imei.toString()
                    )
                    vehicle.devices.add(newDevice)
                    (binding.insideRecyclerView.adapter as? VehiclesDeviceAdapter)?.apply {
                            notifyItemInserted(vehicle.devices.size - 1) }
                    preLoadAllDevices{tryPopulateLinkSpinner()}
                    binding.loading.loaderScreen.visibility = View.GONE
                }
                else{
                    Log.e("API_ERROR_LINK", "Erro ao linkar dispositivo $device no veículo ${vehicle.id}. Código: ${response.code()}. Erro: ${response.errorBody()?.toString()}")
                }
            } catch (e:Exception){} finally { binding.linkVehicleButton.isEnabled = true }
        }

        binding.insideRecyclerView.visibility = View.VISIBLE
        binding.insideLoader.visibility = View.GONE
