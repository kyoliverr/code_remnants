//fun setImage(context: Context, binding: FragmentTaskBinding, task: TaskTest, pickImageLauncher: ActivityResultLauncher<String>, takePictureLauncher: (Uri) -> Unit){
//    if (task.photo.isNullOrEmpty()){
//        binding.updateTaskPhoto.setOnClickListener {
//            pickImageLauncher.launch("image/*")
//        }
//        binding.updateTaskCamera.setOnClickListener {
//            val imageFile = File.createTempFile("cameraphoto", ".jpg", context.cacheDir)
//            val imageUri = FileProvider.getUriForFile(
//                context,
//                "${context.packageName}.provider",
//                imageFile
//            )
//            takePictureLauncher(imageUri)
//        }
//    }
//    else {
//        binding.updateTaskPhoto.visibility = View.GONE
//        binding.updateTaskCamera.visibility = View.GONE
//        val photo = base64ToImage(task.photo!!)
//        photo?.let {
//            binding.previewImage.setImageBitmap(it)
//        }
//        binding.previewImage.visibility = View.VISIBLE
//    }
//    binding.previewImage.setOnLongClickListener {
//        //imageOptions(context, binding, pickImageLauncher, takePictureLauncher)
//        true
//    }
//}

//fun setClient(context: Context, binding: FragmentTaskBinding, task: TaskTest){
//    if (task.client_signature.isNullOrEmpty()){
//        binding.updateClient.visibility = View.VISIBLE
//        binding.updateClientPhoto.visibility = View.GONE
//        //binding.updateClient.setOnClickListener {
//        //    drawSignature(context, binding,  binding.updateClient, binding.updateClientPhoto, null,{}, {})
//        //}
//        //drawSignature(context, binding,  binding.updateClient, binding.updateClientPhoto, null,{}, {})
//    }
//    else{
//        binding.updateClient.visibility = View.GONE
//        val signature = base64ToImage(task.client_signature!!)
//        signature?.let {
//            binding.updateClientPhoto.setImageBitmap(it)
//        }
//        binding.updateClientPhoto.visibility = View.VISIBLE
//    }
//    binding.updateClientPhoto.setOnClickListener {
//        //reSignPopup(context, binding.updateClient, binding.updateClientPhoto, {
//        //    binding.updateClient.setOnClickListener { drawSignature(context, binding,  binding.updateClient,
//        //        binding.updateClientPhoto, null,{}, {}) }
//        //}, {})
//    }
//}
