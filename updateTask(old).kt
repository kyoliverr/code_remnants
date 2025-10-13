package com.example.buscalog.function.task

import android.content.Context
import android.net.Uri
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import com.example.buscalog.data.TaskTest
import com.example.buscalog.data.UpdateTask
import com.example.buscalog.databinding.FragmentTaskBinding
import com.example.buscalog.function.showError
import com.example.buscalog.repository.TaskRepository.updateTask
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun taskUpdateButton(context: Context, binding: FragmentTaskBinding, task: TaskTest, pickImageLauncher: ActivityResultLauncher<String>,
                     takePictureLauncher: (Uri) -> Unit, onSuccess: () -> Unit, onError: () -> Unit) {
    clearUpdateTaskUI(binding)
    binding.updateTaskName.setText(task.taskName)
    binding.updateTaskResponsible.setText(task.name)
    binding.updateTaskDescription.setText(task.description)
    //binding.updateTaskStatus.setText(task.status)
    setImage(context, binding, task, pickImageLauncher, takePictureLauncher)
    setEmployee(context, binding, task)
    setClient(context, binding, task)

    binding.hiddenUpdateTaskLayout.visibility = View.VISIBLE

    binding.updateTaskButton.setOnClickListener {
        val photoBitmap = getBitmapFromImageView(binding.previewImage)
        val photo = photoBitmap?.let { imageToBase64(it) }

        val employeeBitmap = getBitmapFromImageView(binding.updateEmployeePhoto)
        val employeeSignature = employeeBitmap?.let { imageToBase64(it) }

        val clientBitmap = getBitmapFromImageView(binding.updateClientPhoto)
        val clientSignature = clientBitmap?.let { imageToBase64(it) }//null for a while

        val update = UpdateTask(
            finish_datetime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date()),
            latitude = 0.0, longitude = 0.0, //get from actual location
            fields = null
        )
        when{
            binding.updateTaskName.text.isEmpty() -> showError(context, "Nome da tarefa não pode estar vazio")
            else -> {
                updateTask(context, task.id, update,
                    {
                        binding.hiddenUpdateTaskLayout.visibility = View.GONE; onSuccess() }, { onError() })
            }
        }
    }
    binding.hiddenUpdateTaskBody.setOnClickListener {
        binding.hiddenUpdateTaskLayout.visibility = View.GONE; onError()
    }
    binding.cancelUpdateTaskButton.setOnClickListener {
        binding.hiddenUpdateTaskLayout.visibility = View.GONE; onError()
    }
    binding.updateTask.setOnClickListener {}
}

  <LinearLayout
                android:tag="responsible"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="vertical"
                android:layout_marginBottom="8dp">

                <TextView
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:textSize="14sp"
                    android:textStyle="bold"
                    android:text="Responsável*" />

                <EditText
                    android:id="@+id/updateTaskResponsible"
                    android:paddingVertical="10dp"
                    android:paddingHorizontal="16dp"
                    android:hint="Nome do Responsável*"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:background="@drawable/border_edit"
                    android:layout_marginTop="2dp"
                    android:layout_marginBottom="10dp"
                    android:textColorHint="@color/textColorHint" />
            </LinearLayout> <!-- Document Responsible -->
            <FrameLayout
                android:tag="photo"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginVertical="10dp">

                <ImageView
                    android:id="@+id/previewImage"
                    android:layout_width="match_parent"
                    android:layout_height="200dp"
                    android:scaleType="centerCrop"
                    android:visibility="gone" />
                <include
                    android:id="@+id/photo"
                    layout="@layout/reuse_photo_options"/>

                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:orientation="horizontal"
                    android:layout_gravity="bottom|center_horizontal"
                    android:padding="8dp">

                    <TextView
                        android:id="@+id/updateTaskPhoto"
                        android:paddingVertical="10dp"
                        android:paddingHorizontal="16dp"
                        android:text="Adicionar foto relacionada"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_weight="1"
                        android:background="@drawable/border_edit"
                        android:elevation="4dp"
                        app:drawableEndCompat="@drawable/icon_image"
                        android:focusable="true"
                        android:clickable="true" />

                    <ImageButton
                        android:id="@+id/updateTaskCamera"
                        android:layout_gravity="center"
                        android:padding="7dp"
                        android:src="@drawable/icon_camera"
                        android:layout_width="wrap_content"
                        android:layout_height="match_parent"
                        android:layout_marginStart="8dp"
                        android:background="@drawable/border_edit" />
                </LinearLayout>
            </FrameLayout> <!-- Photo -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:orientation="vertical"
                android:layout_marginVertical="10dp">

                <Button android:id="@+id/updateEmployee"
                    android:paddingVertical="5dp"
                    android:layout_marginHorizontal="10dp"
                    android:textStyle="bold"
                    android:text="Assinar Tarefa"
                    android:visibility="visible"
                    android:textColor="@color/white"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    app:backgroundTint="@color/blue"
                    android:background="@drawable/border_button" />

                <include
                    android:id="@+id/sign_method"
                    layout="@layout/reuse_signature_method" />

                <ImageButton
                    android:id="@+id/updateEmployeePhoto"
                    android:visibility="gone"
                    android:layout_width="match_parent"
                    android:background="@drawable/border_edit"
                    android:layout_height="200dp"
                    android:layout_marginTop="10dp"/>

            </LinearLayout> <!-- employee Signature -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:visibility="visible"
                android:orientation="vertical"
                android:layout_marginVertical="10dp">

                <Button android:id="@+id/updateClient"
                    android:paddingVertical="5dp"
                    android:layout_marginHorizontal="10dp"
                    android:textStyle="bold"
                    android:text="Assinatura do Cliente"
                    android:textColor="@color/white"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    app:backgroundTint="@color/blue"
                    android:background="@drawable/border_button" />

                <ImageButton
                    android:id="@+id/updateClientPhoto"
                    android:visibility="gone"
                    android:layout_width="match_parent"
                    android:background="@drawable/border_edit"
                    android:layout_height="200dp"
                    android:layout_marginTop="10dp"/>

            </LinearLayout> <!-- client Signature -->
