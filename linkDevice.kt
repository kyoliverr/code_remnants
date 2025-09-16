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


private fun unlinkDeviceToVehicle(vehicle: Vehicle, deviceId: Int){
        binding.insideLoader.visibility = View.VISIBLE
        val permission = "vehicle.unlinkDevice"
        val unlinkDevicePermission = getPermission(requireContext(), permission)
        val api = RetrofitInstance.getApiService(requireContext(), permission)
        if (unlinkDevicePermission) {
            lifecycleScope.launch {
                try {
                    val response = api.unlinkDeviceFromVehicle(deviceId)
                    if (response.isSuccessful) {
                        //Log.d("API_SUCCESS", "Status: ${response.body()?.status}, Message: ${response.body()?.text}, Dispositivo $deviceId desvinculado do veículo")
                        Toast.makeText(requireContext(), "Dispositivo desvinculado", Toast.LENGTH_SHORT).show()

                        val index = vehicle.devices.indexOfFirst { it.id == deviceId }
                        if (index != -1) {
                            vehicle.devices.removeAt(index)
                            (binding.insideRecyclerView.adapter as? VehiclesDeviceAdapter)?.apply { notifyItemRemoved(index) }
                        }
                        preLoadAllDevices{tryPopulateLinkSpinner()}
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("API_ERROR_LINK", "Erro ao desvincular dispositivo $deviceId. Código: ${response.code()}. Erro: $errorBody")
                    }
                } catch (e: Exception) {}
            }
        }
        binding.insideLoader.visibility = View.GONE
        binding.insideRecyclerView.visibility = View.VISIBLE
    } //Destroy device to vehicle
