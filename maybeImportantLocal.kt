private lateinit var fusedLocationClient: FusedLocationProviderClient

fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        getLocation(requireContext(), requireActivity(), fusedLocationClient)
        Configuration.getInstance().load(
            requireContext(),
            PreferenceManager.getDefaultSharedPreferences(requireContext())
        )
