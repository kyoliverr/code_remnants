val timePicker = TimePickerDialog(
                        context,
                        R.style.CustomTimePickerDialog,
                        { _, hourOfDay, minute ->
                            val selectedDateTime = String.format(
                                "%04d/%02d/%02d %02d:%02d",
                                year, month + 1, dayOfMonth, hourOfDay, minute
                            )
                            setText(selectedDateTime)
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    )
                    timePicker.show()
