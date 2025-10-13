package com.example.buscalog.function.task

import android.view.View
import com.example.buscalog.data.TaskTest
import com.example.buscalog.databinding.FragmentTaskBinding
import com.example.buscalog.function.orNull
import com.example.buscalog.function.showError

fun taskReadButton(binding: FragmentTaskBinding, task: TaskTest){
    clearReadTaskUI(binding)

    binding.taskName.text = task.taskName
    binding.taskResponsible.text = task.name.orNull()
    binding.taskDescription.text = task.description.orNull()
    bindPhoto(task.photo, binding)
    bindEmployee(task.employee_signature, binding)
    bindClient(task.client_signature, binding)
    binding.taskEndDate.text = task.datetime.orNull()

    binding.hiddenTaskInfoLayout.visibility = View.VISIBLE

    binding.taskPhoto.setOnClickListener {
        binding.taskImage.imageFull.setImageBitmap(base64ToImage(task.photo!!))

        binding.taskImage.image.visibility = View.VISIBLE

        binding.taskImage.imageDownload.setOnClickListener{
            val bitmap = base64ToImage(task.photo!!)
            bitmap?.let {
                saveImageToGallery(binding.root.context, it)
                showError(binding.root.context, "Imagem salva com sucesso!")
            }
        }
        binding.taskImage.hiddenImageBody.setOnClickListener {
            binding.taskImage.image.visibility = View.GONE
        }
        binding.taskImage.imageBox.setOnClickListener {}
    }
    binding.hiddenTaskInfoBody.setOnClickListener {
        binding.hiddenTaskInfoLayout.visibility = View.GONE
    }
    binding.moreTaskInfo.setOnClickListener {}
}

package com.example.buscalog.function.task

import android.view.View
import com.example.buscalog.databinding.FragmentTaskBinding

fun bindPhoto(photoCode: String? = null, binding: FragmentTaskBinding){
    if (photoCode != null){
        binding.taskPhoto.visibility = View.VISIBLE
        val photo = base64ToImage(photoCode)
        photo?.let {
            binding.taskPhoto.setImageBitmap(it)
        }
    }
    else {
        binding.taskPhoto.visibility = View.GONE
    }
}

fun bindEmployee(signatureCode: String? = null, binding: FragmentTaskBinding){
    if (signatureCode != null) {
        binding.employeeSignature.taskUnassigned.visibility = View.GONE
        val signature = base64ToImage(signatureCode)
        binding.employeeSignature.taskSigned.setImageBitmap(signature)
        binding.employeeSignature.taskSigned.visibility = View.VISIBLE
    }
    else {
        binding.employeeSignature.taskSigned.visibility = View.GONE
        binding.employeeSignature.taskUnassigned.visibility = View.VISIBLE
    }
}

fun bindClient(signatureCode: String? = null, binding: FragmentTaskBinding){
    if (signatureCode != null) {
        binding.clientSignature.taskUnassigned.visibility = View.GONE
        val signature = base64ToImage(signatureCode)
        binding.clientSignature.taskSigned.setImageBitmap(signature)
        binding.clientSignature.taskSigned.visibility = View.VISIBLE
    }
    else {
        binding.clientSignature.taskSigned.visibility = View.GONE
        binding.clientSignature.taskUnassigned.visibility = View.VISIBLE
    }
}
