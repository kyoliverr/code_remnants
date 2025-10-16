            is FrameLayout -> {
                for (j in 0 until view.childCount) {
                    val inner = view.getChildAt(j)

                    if (inner is LinearLayout) {
                        var image: String? = null; var label: String? = null

                        for (k in 0 until inner.childCount) {
                            val child = inner.getChildAt(k)
                            if (child is ImageView && child.visibility == View.VISIBLE) {
                                val bitmap = (child.drawable as? BitmapDrawable)?.bitmap
                                bitmap?.let { image = imageToBase64(it) }
                            }
                            if (child is EditText) {
                                label = child.text.toString()
                            }
                        }
                        when (inner.tag?.toString()){
                            "image" -> updatedFields.add(TaskFields(label, "image", image))
                        }
                    }
                }
            }
