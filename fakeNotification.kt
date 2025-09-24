private fun fakeNotificationList() {
        binding.notificationRecycle.loader.visibility = View.VISIBLE
        binding.notificationRecycle.recyclerView.visibility = View.GONE

        gridLayoutManager = GridLayoutManager(requireContext(), 1)
        binding.notificationRecycle.recyclerView.layoutManager = gridLayoutManager

        notificationDataList = ArrayList()
        adapter = SeeNotificationsAdapter(requireContext(), notificationDataList, this)
        binding.notificationRecycle.recyclerView.adapter = adapter

        val fakeList = arrayListOf(
            SendableNotification(
                id = 1,
                title = "Nova Atualização",
                message = "Seu aplicativo foi atualizado com sucesso!",
                type = "INFO",
                code = 1001,
                email = "sim",
                push = "sim",
                notificated_by_email = true,
                notificated_by_push = true,
                created_at = "2025-09-16 10:00",
                updated_at = "2025-09-16 10:05",
                user = NotificationUser(id = 1, name = "Admin")
            ),
            SendableNotification(
                id = 2,
                title = "Aviso de Manutenção",
                message = "O sistema ficará indisponível amanhã às 02:00h.",
                type = "WARNING",
                code = 2001,
                email = "não",
                push = "sim",
                notificated_by_email = false,
                notificated_by_push = true,
                created_at = "2025-09-15 22:30",
                updated_at = "2025-09-15 22:45",
                user = NotificationUser(id = 2, name = "Suporte")
            ),
            SendableNotification(
                id = 3,
                title = "Erro Crítico",
                message = "Falha na comunicação com o servidor.",
                type = "ERROR",
                code = 5001,
                email = "sim",
                push = "não",
                notificated_by_email = true,
                notificated_by_push = false,
                created_at = "2025-09-14 08:00",
                updated_at = "2025-09-14 08:10",
                user = NotificationUser(id = 3, name = "Sistema")
            )
        )

        notificationDataList.clear()
        notificationDataList.addAll(fakeList)
        adapter.updateFullList(notificationDataList)

        binding.notificationRecycle.loader.visibility = View.GONE
        binding.notificationRecycle.recyclerView.visibility = View.VISIBLE
    }
