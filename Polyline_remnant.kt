val arrow = android.graphics.Path().apply {
            moveTo(10f, 0f)
            lineTo(-10f, -6f)
            lineTo(-10f, 6f)
            close()
        }

        polyline.outlinePaint.pathEffect = android.graphics.PathDashPathEffect(
            arrow,
            60f,
            0f,
            android.graphics.PathDashPathEffect.Style.ROTATE
        )
