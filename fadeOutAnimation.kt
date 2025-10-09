binding.photo.photoOptions.animate()
    .alpha(0f)
    .setDuration(150)
    .withEndAction { binding.photo.photoOptions.visibility = View.GONE }
    .start()
