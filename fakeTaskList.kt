    private fun fakeTaskList(){
        binding.taskRecycle.loader.visibility = View.VISIBLE
        binding.taskRecycle.recyclerView.visibility = View.GONE

        gridLayoutManager = GridLayoutManager(requireContext(), 1)
        binding.taskRecycle.recyclerView.layoutManager = gridLayoutManager

        taskDataList = ArrayList()
        adapter = TasksPendingAdapter(requireContext(), taskDataList, this)
        binding.taskRecycle.recyclerView.adapter = adapter

        var image: String? = null
        getAllTasks(requireContext(), {
            task ->
            image = task[1].photo

            val fakeFields = arrayListOf<TaskFields>()
            fakeFields.add(TaskFields("Responsável", "text", "Vitor Hugo Official"))
            fakeFields.add(TaskFields("Hornet inseto bem ali", "image", image))
            fakeFields.add(TaskFields(null, "image", null))
            fakeFields.add(TaskFields("Número de Identificação", "number", "123456"))
            fakeFields.add(TaskFields("Assinatura do Responsável", "signature", null))
            val fakeList = arrayListOf(
                Task(
                    id = 1, name = "Entrega de carga #142", description = "Entrega de papel encomendada por empresa X",
                    status = "Pendente", address = "Rua dos bobos, 0",
                    begin_datetime = "25-10-16 10:00:00", end_datetime = null,
                    enterprise = TaskEnterprise(1, "Buscalog"),
                    fields = fakeFields
                )
            )

            taskDataList = updateList(taskDataList, fakeList)
            adapter.updateFullList(taskDataList)

            binding.taskRecycle.loader.visibility = View.GONE
            binding.taskRecycle.recyclerView.visibility = View.VISIBLE
        }, {})
    } //i can remove already
