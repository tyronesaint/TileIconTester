package com.example.tiletester

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import androidx.core.graphics.withSave
import java.io.File

object IconHelper {

    private fun dpToPx(ctx: Context, dp: Int): Int {
        return (dp * ctx.resources.displayMetrics.density).toInt()
    }

    enum class Scale { SMALL, MEDIUM, LARGE, FULL }

    private fun scaleFactor(scale: Scale): Float = when (scale) {
        Scale.SMALL -> 0.30f
        Scale.MEDIUM -> 0.60f
        Scale.LARGE -> 0.85f
        Scale.FULL -> 1.00f
    }

    /** 通用图标入口 */
    fun icon(context: Context, name: String, scale: Scale = Scale.MEDIUM): Icon {
        val size = dpToPx(context, 48)
        val bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.color = 0xFFFFFFFF.toInt()

        val factor = scaleFactor(scale)
        val r = size / 2f * factor
        val cx = size / 2f
        val cy = size / 2f

        when (name) {
            // ===== 基础形状 =====
            "circle" -> c.drawCircle(cx, cy, r, p)
            "square" -> c.drawRect(cx - r, cy - r, cx + r, cy + r, p)
            "triangle" -> drawTriangle(c, p, cx, cy, r)
            "cross" -> drawCross(c, p, cx, cy, r)
            "star" -> drawStar(c, p, cx, cy, r)
            "diamond" -> drawDiamond(c, p, cx, cy, r)
            "hexagon" -> drawHexagon(c, p, cx, cy, r)
            "pentagon" -> drawPentagon(c, p, cx, cy, r)

            // ===== 功能图标 =====
            "search" -> drawSearch(c, p, cx, cy, r)
            "home" -> drawHome(c, p, cx, cy, r)
            "settings" -> drawSettings(c, p, cx, cy, r)
            "play" -> drawPlay(c, p, cx, cy, r)
            "pause" -> drawPause(c, p, cx, cy, r)
            "mail" -> drawMail(c, p, cx, cy, r)
            "link" -> drawLink(c, p, cx, cy, r)
            "user" -> drawUser(c, p, cx, cy, r)
            "lock" -> drawLock(c, p, cx, cy, r)
            "key" -> drawKey(c, p, cx, cy, r)
            "bell" -> drawBell(c, p, cx, cy, r)
            "heart" -> drawHeart(c, p, cx, cy, r)
            "arrow_right" -> drawArrow(c, p, cx, cy, r, 0f)
            "arrow_up" -> drawArrow(c, p, cx, cy, r, 270f)
            "arrow_down" -> drawArrow(c, p, cx, cy, r, 90f)
            "arrow_left" -> drawArrow(c, p, cx, cy, r, 180f)
            "check" -> drawCheck(c, p, cx, cy, r)
            "close" -> drawClose(c, p, cx, cy, r)
            "menu" -> drawMenu(c, p, cx, cy, r)
            "refresh" -> drawRefresh(c, p, cx, cy, r)
            "download" -> drawDownload(c, p, cx, cy, r)
            "upload" -> drawUpload(c, p, cx, cy, r)
            "wifi" -> drawWifi(c, p, cx, cy, r)
            "battery" -> drawBattery(c, p, cx, cy, r)
            "location" -> drawLocation(c, p, cx, cy, r)
            "flag" -> drawFlag(c, p, cx, cy, r)
            "bookmark" -> drawBookmark(c, p, cx, cy, r)
            "calendar" -> drawCalendar(c, p, cx, cy, r)
            "clock" -> drawClock(c, p, cx, cy, r)
            "camera" -> drawCamera(c, p, cx, cy, r)
            "music" -> drawMusic(c, p, cx, cy, r)
            "folder" -> drawFolder(c, p, cx, cy, r)
            "file" -> drawFile(c, p, cx, cy, r)
            "trash" -> drawTrash(c, p, cx, cy, r)
            "eye" -> drawEye(c, p, cx, cy, r)
            "moon" -> drawMoon(c, p, cx, cy, r)
            "sun" -> drawSun(c, p, cx, cy, r)
            "cloud" -> drawCloud(c, p, cx, cy, r)
            "fire" -> drawFire(c, p, cx, cy, r)
            "bolt" -> drawBolt(c, p, cx, cy, r)

            // ===== 新增 =====
            "earth" -> drawEarth(c, p, cx, cy, r)
            "car" -> drawCar(c, p, cx, cy, r)
            "rocket" -> drawRocket(c, p, cx, cy, r)
            "zashboard" -> drawZashboard(c, p, cx, cy, r)
            "apple_home" -> drawAppleHome(c, p, cx, cy, r)
            "ha_home" -> drawHAHome(c, p, cx, cy, r)

            else -> c.drawCircle(cx, cy, r, p)
        }

        return Icon.createWithBitmap(bmp)
    }

    // ===== 基础形状 =====
    private fun drawTriangle(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        val path = Path()
        path.moveTo(cx, cy - r)
        path.lineTo(cx - r * 0.866f, cy + r * 0.5f)
        path.lineTo(cx + r * 0.866f, cy + r * 0.5f)
        path.close()
        c.drawPath(path, p)
    }

    private fun drawCross(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        val t = r * 0.25f
        c.drawRect(cx - t, cy - r, cx + t, cy + r, p)
        c.drawRect(cx - r, cy - t, cx + r, cy + t, p)
    }

    private fun drawStar(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        val path = Path()
        val outer = r
        val inner = r * 0.4f
        for (i in 0 until 10) {
            val rad = if (i % 2 == 0) outer else inner
            val angle = Math.PI / 2 + i * Math.PI / 5
            val x = cx + (rad * kotlin.math.cos(angle)).toFloat()
            val y = cy - (rad * kotlin.math.sin(angle)).toFloat()
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        path.close()
        c.drawPath(path, p)
    }

    private fun drawDiamond(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        val path = Path()
        path.moveTo(cx, cy - r)
        path.lineTo(cx + r, cy)
        path.lineTo(cx, cy + r)
        path.lineTo(cx - r, cy)
        path.close()
        c.drawPath(path, p)
    }

    private fun drawHexagon(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        val path = Path()
        for (i in 0 until 6) {
            val angle = Math.PI / 6 + i * Math.PI / 3
            val x = cx + (r * kotlin.math.cos(angle)).toFloat()
            val y = cy + (r * kotlin.math.sin(angle)).toFloat()
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        path.close()
        c.drawPath(path, p)
    }

    private fun drawPentagon(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        val path = Path()
        for (i in 0 until 5) {
            val angle = Math.PI / 2 + i * 2 * Math.PI / 5
            val x = cx + (r * kotlin.math.cos(angle)).toFloat()
            val y = cy - (r * kotlin.math.sin(angle)).toFloat()
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        path.close()
        c.drawPath(path, p)
    }

    // ===== 功能图标 =====
    private fun drawSearch(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        p.style = Paint.Style.STROKE
        p.strokeWidth = r * 0.15f
        c.drawCircle(cx - r * 0.2f, cy - r * 0.2f, r * 0.5f, p)
        p.style = Paint.Style.FILL
        val path = Path()
        path.moveTo(cx + r * 0.15f, cy + r * 0.15f)
        path.lineTo(cx + r * 0.5f, cy + r * 0.5f)
        path.lineTo(cx + r * 0.35f, cy + r * 0.65f)
        path.close()
        c.drawPath(path, p)
    }

    private fun drawHome(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        val path = Path()
        path.moveTo(cx, cy - r)
        path.lineTo(cx + r, cy)
        path.lineTo(cx + r * 0.7f, cy)
        path.lineTo(cx + r * 0.7f, cy + r)
        path.lineTo(cx - r * 0.7f, cy + r)
        path.lineTo(cx - r * 0.7f, cy)
        path.lineTo(cx - r, cy)
        path.close()
        c.drawPath(path, p)
    }

    private fun drawSettings(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        c.drawCircle(cx, cy, r * 0.35f, p)
        p.style = Paint.Style.STROKE
        p.strokeWidth = r * 0.2f
        for (i in 0 until 6) {
            val angle = i * Math.PI / 3
            val x1 = cx + (r * 0.5f * kotlin.math.cos(angle)).toFloat()
            val y1 = cy + (r * 0.5f * kotlin.math.sin(angle)).toFloat()
            val x2 = cx + (r * kotlin.math.cos(angle)).toFloat()
            val y2 = cy + (r * kotlin.math.sin(angle)).toFloat()
            c.drawLine(x1, y1, x2, y2, p)
        }
        p.style = Paint.Style.FILL
    }

    private fun drawPlay(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        val path = Path()
        path.moveTo(cx - r * 0.5f, cy - r)
        path.lineTo(cx + r, cy)
        path.lineTo(cx - r * 0.5f, cy + r)
        path.close()
        c.drawPath(path, p)
    }

    private fun drawPause(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        val w = r * 0.3f
        c.drawRect(cx - r * 0.4f, cy - r, cx - r * 0.1f, cy + r, p)
        c.drawRect(cx + r * 0.1f, cy - r, cx + r * 0.4f, cy + r, p)
    }

    private fun drawMail(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        c.drawRect(cx - r, cy - r * 0.6f, cx + r, cy + r * 0.6f, p)
        val path = Path()
        path.moveTo(cx - r, cy - r * 0.6f)
        path.lineTo(cx, cy)
        path.lineTo(cx + r, cy - r * 0.6f)
        p.style = Paint.Style.STROKE
        p.strokeWidth = r * 0.08f
        c.drawPath(path, p)
        p.style = Paint.Style.FILL
    }

    private fun drawLink(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        p.style = Paint.Style.STROKE
        p.strokeWidth = r * 0.2f
        c.drawCircle(cx - r * 0.3f, cy, r * 0.4f, p)
        c.drawCircle(cx + r * 0.3f, cy, r * 0.4f, p)
        p.style = Paint.Style.FILL
    }

    private fun drawUser(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        c.drawCircle(cx, cy - r * 0.3f, r * 0.35f, p)
        val path = Path()
        path.moveTo(cx - r, cy + r)
        path.quadTo(cx, cy - r * 0.1f, cx + r, cy + r)
        path.close()
        c.drawPath(path, p)
    }

    private fun drawLock(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        p.style = Paint.Style.STROKE
        p.strokeWidth = r * 0.12f
        c.drawArc(cx - r * 0.4f, cy - r, cx + r * 0.4f, cy - r * 0.2f, 180f, 180f, false, p)
        p.style = Paint.Style.FILL
        c.drawRect(cx - r * 0.5f, cy - r * 0.2f, cx + r * 0.5f, cy + r * 0.6f, p)
        p.color = 0xFF000000.toInt()
        c.drawCircle(cx, cy + r * 0.15f, r * 0.1f, p)
        p.color = 0xFFFFFFFF.toInt()
    }

    private fun drawKey(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        c.drawCircle(cx - r * 0.3f, cy - r * 0.3f, r * 0.25f, p)
        p.style = Paint.Style.STROKE
        p.strokeWidth = r * 0.15f
        c.drawLine(cx - r * 0.15f, cy - r * 0.15f, cx + r * 0.5f, cy + r * 0.5f, p)
        p.style = Paint.Style.FILL
        c.drawRect(cx + r * 0.35f, cy + r * 0.35f, cx + r * 0.55f, cy + r * 0.55f, p)
    }

    private fun drawBell(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        val path = Path()
        path.moveTo(cx - r * 0.7f, cy + r * 0.3f)
        path.quadTo(cx - r * 0.7f, cy - r, cx, cy - r)
        path.quadTo(cx + r * 0.7f, cy - r, cx + r * 0.7f, cy + r * 0.3f)
        path.lineTo(cx - r * 0.7f, cy + r * 0.3f)
        path.close()
        c.drawPath(path, p)
        c.drawCircle(cx, cy + r * 0.5f, r * 0.15f, p)
    }

    private fun drawHeart(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        val path = Path()
        path.moveTo(cx, cy + r * 0.3f)
        path.cubicTo(cx - r, cy - r * 0.5f, cx - r, cy - r, cx - r * 0.3f, cy - r * 0.3f)
        path.cubicTo(cx - r * 0.1f, cy - r * 0.1f, cx, cy - r * 0.1f, cx, cy - r * 0.1f)
        path.cubicTo(cx, cy - r * 0.1f, cx + r * 0.1f, cy - r * 0.1f, cx + r * 0.3f, cy - r * 0.3f)
        path.cubicTo(cx + r, cy - r, cx + r, cy - r * 0.5f, cx, cy + r * 0.3f)
        path.close()
        c.drawPath(path, p)
    }

    private fun drawArrow(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float, rotation: Float) {
        c.withSave {
            c.rotate(rotation, cx, cy)
            val path = Path()
            path.moveTo(cx + r, cy)
            path.lineTo(cx - r * 0.3f, cy - r * 0.5f)
            path.lineTo(cx - r * 0.3f, cy + r * 0.5f)
            path.close()
            c.drawPath(path, p)
            c.drawRect(cx - r, cy - r * 0.2f, cx - r * 0.3f, cy + r * 0.2f, p)
        }
    }

    private fun drawCheck(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        p.style = Paint.Style.STROKE
        p.strokeWidth = r * 0.18f
        p.strokeCap = Paint.Cap.ROUND
        p.strokeJoin = Paint.Join.ROUND
        val path = Path()
        path.moveTo(cx - r * 0.5f, cy)
        path.lineTo(cx - r * 0.1f, cy + r * 0.5f)
        path.lineTo(cx + r * 0.6f, cy - r * 0.4f)
        c.drawPath(path, p)
        p.style = Paint.Style.FILL
    }

    private fun drawClose(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        p.style = Paint.Style.STROKE
        p.strokeWidth = r * 0.18f
        p.strokeCap = Paint.Cap.ROUND
        c.drawLine(cx - r * 0.5f, cy - r * 0.5f, cx + r * 0.5f, cy + r * 0.5f, p)
        c.drawLine(cx + r * 0.5f, cy - r * 0.5f, cx - r * 0.5f, cy + r * 0.5f, p)
        p.style = Paint.Style.FILL
    }

    private fun drawMenu(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        val h = r * 0.15f
        c.drawRect(cx - r * 0.6f, cy - r * 0.4f, cx + r * 0.6f, cy - r * 0.25f, p)
        c.drawRect(cx - r * 0.6f, cy - h / 2, cx + r * 0.6f, cy + h / 2, p)
        c.drawRect(cx - r * 0.6f, cy + r * 0.25f, cx + r * 0.6f, cy + r * 0.4f, p)
    }

    private fun drawRefresh(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        p.style = Paint.Style.STROKE
        p.strokeWidth = r * 0.12f
        c.drawArc(cx - r * 0.7f, cy - r * 0.7f, cx + r * 0.7f, cy + r * 0.7f, 30f, 300f, false, p)
        val path = Path()
        path.moveTo(cx + r * 0.5f, cy - r * 0.3f)
        path.lineTo(cx + r * 0.7f, cy - r * 0.1f)
        path.lineTo(cx + r * 0.4f, cy + r * 0.1f)
        path.close()
        p.style = Paint.Style.FILL
        c.drawPath(path, p)
    }

    private fun drawDownload(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        c.drawRect(cx - r * 0.15f, cy - r * 0.5f, cx + r * 0.15f, cy + r * 0.3f, p)
        val path = Path()
        path.moveTo(cx - r * 0.4f, cy + r * 0.3f)
        path.lineTo(cx, cy + r * 0.7f)
        path.lineTo(cx + r * 0.4f, cy + r * 0.3f)
        path.close()
        c.drawPath(path, p)
        c.drawRect(cx - r * 0.5f, cy + r * 0.7f, cx + r * 0.5f, cy + r * 0.85f, p)
    }

    private fun drawUpload(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        c.drawRect(cx - r * 0.15f, cy - r * 0.3f, cx + r * 0.15f, cy + r * 0.5f, p)
        val path = Path()
        path.moveTo(cx - r * 0.4f, cy - r * 0.3f)
        path.lineTo(cx, cy - r * 0.7f)
        path.lineTo(cx + r * 0.4f, cy - r * 0.3f)
        path.close()
        c.drawPath(path, p)
        c.drawRect(cx - r * 0.5f, cy + r * 0.7f, cx + r * 0.5f, cy + r * 0.85f, p)
    }

    private fun drawWifi(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        p.style = Paint.Style.STROKE
        p.strokeWidth = r * 0.1f
        p.strokeCap = Paint.Cap.ROUND
        c.drawArc(cx - r * 0.3f, cy - r * 0.3f, cx + r * 0.3f, cy + r * 0.3f, 220f, 100f, false, p)
        c.drawArc(cx - r * 0.55f, cy - r * 0.55f, cx + r * 0.55f, cy + r * 0.55f, 220f, 100f, false, p)
        c.drawArc(cx - r * 0.8f, cy - r * 0.8f, cx + r * 0.8f, cy + r * 0.8f, 220f, 100f, false, p)
        p.style = Paint.Style.FILL
        c.drawCircle(cx, cy + r * 0.3f, r * 0.1f, p)
    }

    private fun drawBattery(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        c.drawRect(cx - r * 0.6f, cy - r * 0.4f, cx + r * 0.6f, cy + r * 0.4f, p)
        c.drawRect(cx + r * 0.6f, cy - r * 0.15f, cx + r * 0.75f, cy + r * 0.15f, p)
        p.color = 0xFF000000.toInt()
        c.drawRect(cx - r * 0.4f, cy - r * 0.25f, cx + r * 0.2f, cy + r * 0.25f, p)
        p.color = 0xFFFFFFFF.toInt()
    }

    private fun drawLocation(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        val path = Path()
        path.moveTo(cx, cy - r)
        path.cubicTo(cx + r, cy - r * 0.5f, cx + r, cy + r * 0.3f, cx, cy + r)
        path.cubicTo(cx - r, cy + r * 0.3f, cx - r, cy - r * 0.5f, cx, cy - r)
        path.close()
        c.drawPath(path, p)
        p.color = 0xFF000000.toInt()
        c.drawCircle(cx, cy - r * 0.1f, r * 0.25f, p)
        p.color = 0xFFFFFFFF.toInt()
    }

    private fun drawFlag(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        c.drawRect(cx - r * 0.1f, cy - r, cx + r * 0.1f, cy + r, p)
        val path = Path()
        path.moveTo(cx + r * 0.1f, cy - r * 0.8f)
        path.lineTo(cx + r * 0.8f, cy - r * 0.5f)
        path.lineTo(cx + r * 0.1f, cy - r * 0.2f)
        path.close()
        c.drawPath(path, p)
    }

    private fun drawBookmark(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        val path = Path()
        path.moveTo(cx - r * 0.5f, cy - r)
        path.lineTo(cx + r * 0.5f, cy - r)
        path.lineTo(cx + r * 0.5f, cy + r * 0.5f)
        path.lineTo(cx, cy + r * 0.2f)
        path.lineTo(cx - r * 0.5f, cy + r * 0.5f)
        path.close()
        c.drawPath(path, p)
    }

    private fun drawCalendar(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        c.drawRect(cx - r * 0.7f, cy - r * 0.5f, cx + r * 0.7f, cy + r * 0.6f, p)
        c.drawRect(cx - r * 0.3f, cy - r * 0.7f, cx - r * 0.1f, cy - r * 0.5f, p)
        c.drawRect(cx + r * 0.1f, cy - r * 0.7f, cx + r * 0.3f, cy - r * 0.5f, p)
        p.color = 0xFF000000.toInt()
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                c.drawRect(
                    cx - r * 0.4f + j * r * 0.3f,
                    cy - r * 0.2f + i * r * 0.25f,
                    cx - r * 0.25f + j * r * 0.3f,
                    cy - r * 0.05f + i * r * 0.25f,
                    p
                )
            }
        }
        p.color = 0xFFFFFFFF.toInt()
    }

    private fun drawClock(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        p.style = Paint.Style.STROKE
        p.strokeWidth = r * 0.1f
        c.drawCircle(cx, cy, r * 0.7f, p)
        p.strokeCap = Paint.Cap.ROUND
        p.strokeWidth = r * 0.12f
        c.drawLine(cx, cy, cx, cy - r * 0.4f, p)
        p.strokeWidth = r * 0.08f
        c.drawLine(cx, cy, cx + r * 0.3f, cy + r * 0.2f, p)
        p.style = Paint.Style.FILL
    }

    private fun drawCamera(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        c.drawRect(cx - r * 0.7f, cy - r * 0.4f, cx + r * 0.7f, cy + r * 0.5f, p)
        c.drawRect(cx - r * 0.3f, cy - r * 0.6f, cx + r * 0.3f, cy - r * 0.4f, p)
        p.color = 0xFF000000.toInt()
        c.drawCircle(cx, cy, r * 0.35f, p)
        p.color = 0xFFFFFFFF.toInt()
        c.drawCircle(cx, cy, r * 0.2f, p)
    }

    private fun drawMusic(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        c.drawRect(cx - r * 0.1f, cy - r, cx + r * 0.1f, cy + r * 0.2f, p)
        c.drawOval(cx - r * 0.4f, cy + r * 0.1f, cx + r * 0.2f, cy + r * 0.5f, p)
        val path = Path()
        path.moveTo(cx + r * 0.1f, cy - r)
        path.lineTo(cx + r * 0.5f, cy - r * 0.7f)
        path.lineTo(cx + r * 0.1f, cy - r * 0.4f)
        path.close()
        c.drawPath(path, p)
    }

    private fun drawFolder(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        val path = Path()
        path.moveTo(cx - r * 0.7f, cy - r * 0.3f)
        path.lineTo(cx - r * 0.3f, cy - r * 0.3f)
        path.lineTo(cx - r * 0.1f, cy - r * 0.5f)
        path.lineTo(cx + r * 0.5f, cy - r * 0.5f)
        path.lineTo(cx + r * 0.7f, cy - r * 0.3f)
        path.lineTo(cx + r * 0.7f, cy + r * 0.4f)
        path.lineTo(cx - r * 0.7f, cy + r * 0.4f)
        path.close()
        c.drawPath(path, p)
    }

    private fun drawFile(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        val path = Path()
        path.moveTo(cx - r * 0.5f, cy - r)
        path.lineTo(cx + r * 0.3f, cy - r)
        path.lineTo(cx + r * 0.5f, cy - r * 0.8f)
        path.lineTo(cx + r * 0.5f, cy + r)
        path.lineTo(cx - r * 0.5f, cy + r)
        path.close()
        c.drawPath(path, p)
        p.style = Paint.Style.STROKE
        p.strokeWidth = r * 0.05f
        c.drawLine(cx + r * 0.3f, cy - r, cx + r * 0.3f, cy - r * 0.8f, p)
        c.drawLine(cx + r * 0.3f, cy - r * 0.8f, cx + r * 0.5f, cy - r * 0.8f, p)
        p.style = Paint.Style.FILL
    }

    private fun drawTrash(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        p.style = Paint.Style.STROKE
        p.strokeWidth = r * 0.08f
        c.drawLine(cx - r * 0.4f, cy - r * 0.6f, cx + r * 0.4f, cy - r * 0.6f, p)
        p.style = Paint.Style.FILL
        c.drawRect(cx - r * 0.5f, cy - r * 0.4f, cx + r * 0.5f, cy + r * 0.6f, p)
        p.color = 0xFF000000.toInt()
        c.drawLine(cx - r * 0.2f, cy - r * 0.2f, cx - r * 0.2f, cy + r * 0.3f, p)
        c.drawLine(cx, cy - r * 0.2f, cx, cy + r * 0.3f, p)
        c.drawLine(cx + r * 0.2f, cy - r * 0.2f, cx + r * 0.2f, cy + r * 0.3f, p)
        p.color = 0xFFFFFFFF.toInt()
    }

    private fun drawEye(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        val path = Path()
        path.moveTo(cx - r, cy)
        path.quadTo(cx, cy - r * 0.8f, cx + r, cy)
        path.quadTo(cx, cy + r * 0.8f, cx - r, cy)
        path.close()
        c.drawPath(path, p)
        p.color = 0xFF000000.toInt()
        c.drawCircle(cx, cy, r * 0.3f, p)
        p.color = 0xFFFFFFFF.toInt()
    }

    private fun drawMoon(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        val path = Path()
        path.moveTo(cx + r * 0.3f, cy - r)
        path.cubicTo(cx + r, cy - r, cx + r, cy + r, cx + r * 0.3f, cy + r)
        path.cubicTo(cx + r * 0.7f, cy + r * 0.5f, cx + r * 0.7f, cy - r * 0.5f, cx + r * 0.3f, cy - r)
        path.close()
        c.drawPath(path, p)
    }

    private fun drawSun(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        c.drawCircle(cx, cy, r * 0.4f, p)
        p.strokeWidth = r * 0.08f
        p.strokeCap = Paint.Cap.ROUND
        for (i in 0 until 8) {
            val angle = i * Math.PI / 4
            val x1 = cx + (r * 0.5f * kotlin.math.cos(angle)).toFloat()
            val y1 = cy + (r * 0.5f * kotlin.math.sin(angle)).toFloat()
            val x2 = cx + (r * kotlin.math.cos(angle)).toFloat()
            val y2 = cy + (r * kotlin.math.sin(angle)).toFloat()
            c.drawLine(x1, y1, x2, y2, p)
        }
    }

    private fun drawCloud(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        c.drawCircle(cx - r * 0.3f, cy, r * 0.4f, p)
        c.drawCircle(cx + r * 0.3f, cy, r * 0.4f, p)
        c.drawCircle(cx, cy - r * 0.3f, r * 0.45f, p)
        c.drawRect(cx - r * 0.4f, cy, cx + r * 0.4f, cy + r * 0.3f, p)
    }

    private fun drawFire(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        val path = Path()
        path.moveTo(cx - r * 0.4f, cy + r * 0.5f)
        path.quadTo(cx - r * 0.3f, cy, cx, cy - r)
        path.quadTo(cx + r * 0.3f, cy, cx + r * 0.4f, cy + r * 0.5f)
        path.quadTo(cx + r * 0.2f, cy + r * 0.2f, cx, cy + r * 0.6f)
        path.quadTo(cx - r * 0.2f, cy + r * 0.2f, cx - r * 0.4f, cy + r * 0.5f)
        path.close()
        c.drawPath(path, p)
    }

    private fun drawBolt(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        val path = Path()
        path.moveTo(cx + r * 0.3f, cy - r)
        path.lineTo(cx - r * 0.2f, cy)
        path.lineTo(cx + r * 0.2f, cy)
        path.lineTo(cx - r * 0.3f, cy + r)
        path.lineTo(cx + r * 0.1f, cy + r * 0.1f)
        path.lineTo(cx - r * 0.1f, cy + r * 0.1f)
        path.close()
        c.drawPath(path, p)
    }

    // ===== 新增图标 =====
    private fun drawEarth(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        p.style = Paint.Style.STROKE
        p.strokeWidth = r * 0.08f
        c.drawCircle(cx, cy, r * 0.7f, p)
        c.drawLine(cx - r * 0.7f, cy, cx + r * 0.7f, cy, p)
        c.drawLine(cx, cy - r * 0.35f, cx, cy + r * 0.35f, p)
        val path = Path()
        path.moveTo(cx - r * 0.5f, cy - r * 0.5f)
        path.quadTo(cx, cy - r * 0.2f, cx + r * 0.5f, cy - r * 0.5f)
        path.moveTo(cx - r * 0.5f, cy + r * 0.5f)
        path.quadTo(cx, cy + r * 0.2f, cx + r * 0.5f, cy + r * 0.5f)
        c.drawPath(path, p)
        p.style = Paint.Style.FILL
    }

    private fun drawCar(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        val path = Path()
        path.moveTo(cx - r * 0.8f, cy + r * 0.3f)
        path.lineTo(cx - r * 0.8f, cy)
        path.lineTo(cx - r * 0.4f, cy - r * 0.3f)
        path.lineTo(cx + r * 0.4f, cy - r * 0.3f)
        path.lineTo(cx + r * 0.8f, cy)
        path.lineTo(cx + r * 0.8f, cy + r * 0.3f)
        path.close()
        c.drawPath(path, p)
        p.color = 0xFF000000.toInt()
        c.drawCircle(cx - r * 0.45f, cy + r * 0.3f, r * 0.2f, p)
        c.drawCircle(cx + r * 0.45f, cy + r * 0.3f, r * 0.2f, p)
        p.color = 0xFFFFFFFF.toInt()
    }

    private fun drawRocket(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        val path = Path()
        path.moveTo(cx, cy - r)
        path.lineTo(cx + r * 0.3f, cy - r * 0.3f)
        path.lineTo(cx + r * 0.3f, cy + r * 0.5f)
        path.lineTo(cx, cy + r * 0.7f)
        path.lineTo(cx - r * 0.3f, cy + r * 0.5f)
        path.lineTo(cx - r * 0.3f, cy - r * 0.3f)
        path.close()
        c.drawPath(path, p)
        p.color = 0xFF000000.toInt()
        c.drawCircle(cx, cy, r * 0.2f, p)
        p.color = 0xFFFFFFFF.toInt()
        // 尾翼
        c.drawRect(cx - r * 0.5f, cy + r * 0.3f, cx - r * 0.3f, cy + r * 0.6f, p)
        c.drawRect(cx + r * 0.3f, cy + r * 0.3f, cx + r * 0.5f, cy + r * 0.6f, p)
        // 火焰
        val flame = Path()
        flame.moveTo(cx - r * 0.15f, cy + r * 0.7f)
        flame.lineTo(cx, cy + r)
        flame.lineTo(cx + r * 0.15f, cy + r * 0.7f)
        flame.close()
        c.drawPath(flame, p)
    }

    private fun drawZashboard(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        // 六边形外框
        p.style = Paint.Style.STROKE
        p.strokeWidth = r * 0.12f
        p.strokeCap = Paint.Cap.ROUND
        p.strokeJoin = Paint.Join.ROUND
        val hex = Path()
        for (i in 0 until 6) {
            val angle = Math.PI / 6 + i * Math.PI / 3
            val x = cx + (r * kotlin.math.cos(angle)).toFloat()
            val y = cy + (r * kotlin.math.sin(angle)).toFloat()
            if (i == 0) hex.moveTo(x, y) else hex.lineTo(x, y)
        }
        hex.close()
        c.drawPath(hex, p)
        // 中心 Y 形
        p.style = Paint.Style.FILL
        val yPath = Path()
        yPath.moveTo(cx, cy - r * 0.3f)
        yPath.lineTo(cx + r * 0.25f, cy + r * 0.1f)
        yPath.lineTo(cx + r * 0.1f, cy + r * 0.1f)
        yPath.lineTo(cx + r * 0.1f, cy + r * 0.35f)
        yPath.lineTo(cx - r * 0.1f, cy + r * 0.35f)
        yPath.lineTo(cx - r * 0.1f, cy + r * 0.1f)
        yPath.lineTo(cx - r * 0.25f, cy + r * 0.1f)
        yPath.close()
        c.drawPath(yPath, p)
        // 四个方向箭头（简化）
        val arrowLen = r * 0.25f
        for (i in 0 until 4) {
            val angle = i * Math.PI / 2
            val x = cx + (r * 0.6f * kotlin.math.cos(angle)).toFloat()
            val y = cy + (r * 0.6f * kotlin.math.sin(angle)).toFloat()
            val path = Path()
            path.moveTo(x, y - arrowLen * 0.3f)
            path.lineTo(x + arrowLen * 0.3f, y + arrowLen * 0.3f)
            path.lineTo(x - arrowLen * 0.3f, y + arrowLen * 0.3f)
            path.close()
            c.withSave {
                c.rotate((i * 90 - 90).toFloat(), x, y)
                c.drawPath(path, p)
            }
        }
    }

    private fun drawAppleHome(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        val path = Path()
        path.moveTo(cx, cy - r)
        path.lineTo(cx + r, cy)
        path.lineTo(cx + r * 0.7f, cy)
        path.lineTo(cx + r * 0.7f, cy + r)
        path.lineTo(cx - r * 0.7f, cy + r)
        path.lineTo(cx - r * 0.7f, cy)
        path.lineTo(cx - r, cy)
        path.close()
        c.drawPath(path, p)
        // 内部小房子
        p.color = 0xFF000000.toInt()
        val inner = Path()
        inner.moveTo(cx, cy - r * 0.3f)
        inner.lineTo(cx + r * 0.3f, cy)
        inner.lineTo(cx + r * 0.2f, cy)
        inner.lineTo(cx + r * 0.2f, cy + r * 0.3f)
        inner.lineTo(cx - r * 0.2f, cy + r * 0.3f)
        inner.lineTo(cx - r * 0.2f, cy)
        inner.lineTo(cx - r * 0.3f, cy)
        inner.close()
        c.drawPath(inner, p)
        p.color = 0xFFFFFFFF.toInt()
    }

    private fun drawHAHome(c: Canvas, p: Paint, cx: Float, cy: Float, r: Float) {
        val path = Path()
        path.moveTo(cx, cy - r)
        path.lineTo(cx + r, cy)
        path.lineTo(cx + r * 0.7f, cy)
        path.lineTo(cx + r * 0.7f, cy + r)
        path.lineTo(cx - r * 0.7f, cy + r)
        path.lineTo(cx - r * 0.7f, cy)
        path.lineTo(cx - r, cy)
        path.close()
        c.drawPath(path, p)
        // WiFi 波浪
        p.style = Paint.Style.STROKE
        p.strokeWidth = r * 0.08f
        p.strokeCap = Paint.Cap.ROUND
        c.drawArc(cx - r * 0.3f, cy - r * 0.1f, cx + r * 0.3f, cy + r * 0.5f, 220f, 100f, false, p)
        c.drawArc(cx - r * 0.5f, cy - r * 0.3f, cx + r * 0.5f, cy + r * 0.7f, 220f, 100f, false, p)
        p.style = Paint.Style.FILL
    }

    // ===== 透图/Emoji/图片（保留）=====
    fun transparentBitmap(context: Context, text: String, scale: Scale = Scale.MEDIUM): Icon {
        val size = dpToPx(context, 48)
        val bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.color = 0xFFFFFFFF.toInt()
        val factor = scaleFactor(scale)
        val baseSize = size * when (text.length) {
            1 -> 0.55f
            2 -> 0.40f
            else -> 0.30f
        }
        p.textSize = baseSize * factor / 0.60f
        p.textAlign = Paint.Align.CENTER
        p.typeface = Typeface.DEFAULT_BOLD
        val m = p.fontMetrics
        c.drawText(text, size / 2f, size / 2f - (m.ascent + m.descent) / 2f, p)
        return Icon.createWithBitmap(bmp)
    }

    fun emojiIcon(context: Context, emoji: String, scale: Scale = Scale.MEDIUM): Icon {
        val size = dpToPx(context, 48)
        val bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.color = 0xFFFFFFFF.toInt()
        val factor = scaleFactor(scale)
        p.textSize = size * 0.55f * factor / 0.60f
        p.textAlign = Paint.Align.CENTER
        p.typeface = Typeface.DEFAULT
        val m = p.fontMetrics
        c.drawText(emoji, size / 2f, size / 2f - (m.ascent + m.descent) / 2f, p)
        return Icon.createWithBitmap(bmp)
    }

    fun customImageIcon(context: Context, path: String): Icon {
        val file = if (path.startsWith("file://")) File(Uri.parse(path).path!!) else File(path)
        if (!file.exists()) return transparentBitmap(context, "?", Scale.MEDIUM)
        val src = android.graphics.BitmapFactory.decodeFile(file.absolutePath)
            ?: return transparentBitmap(context, "?", Scale.MEDIUM)
        val size = dpToPx(context, 48)
        val output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val circlePath = Path().apply { addCircle(size / 2f, size / 2f, size / 2f, Path.Direction.CCW) }
        canvas.withSave {
            canvas.clipPath(circlePath)
            val scale = size.toFloat() / kotlin.math.min(src.width, src.height)
            val dx = (size - src.width * scale) / 2f
            val dy = (size - src.height * scale) / 2f
            canvas.drawBitmap(src, null, RectF(dx, dy, dx + src.width * scale, dy + src.height * scale), paint)
        }
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = size * 0.04f
        paint.color = 0xFFFFFFFF.toInt()
        canvas.drawCircle(size / 2f, size / 2f, size / 2f - paint.strokeWidth / 2, paint)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Icon.createWithAdaptiveBitmap(output)
        } else {
            Icon.createWithBitmap(output)
        }
    }

    fun saveCustomImage(context: Context, uri: Uri): String? {
        return try {
            val input = context.contentResolver.openInputStream(uri) ?: return null
            val dir = File(context.filesDir, "custom_icons").apply { mkdirs() }
            val outFile = File(dir, "icon_${System.currentTimeMillis()}.png")
            java.io.FileOutputStream(outFile).use { output -> input.copyTo(output) }
            input.close()
            outFile.absolutePath
        } catch (e: Exception) { null }
    }
}
