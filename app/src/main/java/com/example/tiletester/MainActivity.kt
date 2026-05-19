package com.example.tiletester

import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.service.quicksettings.TileService
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri ?: return@registerForActivityResult
        val path = IconHelper.saveCustomImage(this, uri)
        if (path != null) {
            TestTileCustomImage.customPath = path
            refreshTile(TestTileCustomImage::class.java)
            Toast.makeText(this, "图片已设置", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvInfo = findViewById<TextView>(R.id.tvInfo)
        val btnRefresh = findViewById<Button>(R.id.btnRefresh)
        val btnIconList = findViewById<Button>(R.id.btnIconList)
        val btnTransparent = findViewById<Button>(R.id.btnTransparent)
        val btnEmoji = findViewById<Button>(R.id.btnEmoji)
        val btnSystem = findViewById<Button>(R.id.btnSystem)
        val btnImage = findViewById<Button>(R.id.btnImage)

        tvInfo.text = buildString {
            appendLine("ColorOS 磁贴图标测试 v5")
            appendLine("内置 50+ 个通用简化图标")
            appendLine()
            appendLine("核心发现:")
            appendLine("• Bitmap尺寸对显示大小无影响")
            appendLine("• ColorOS强制缩放到固定槽位")
            appendLine("• 内容占比才是真正影响视觉大小的变量")
            appendLine()
            appendLine("测试项:")
            appendLine("• 内置图标 (50+个)")
            appendLine("• 透图文字 (自定义文字+占比)")
            appendLine("• Emoji (自定义表情+占比)")
            appendLine("• 系统图标 (15个内置)")
            appendLine("• 自定义图片 (相册上传)")
        }

        btnRefresh.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                listOf(
                    TestTileCustomIcon::class.java,
                    TestTileCustomTransparent::class.java,
                    TestTileCustomEmoji::class.java,
                    TestTileCustomSystem::class.java,
                    TestTileCustomImage::class.java
                ).forEach { cls ->
                    TileService.requestListeningState(this, ComponentName(this, cls))
                }
                Toast.makeText(this, "已刷新5个磁贴", Toast.LENGTH_SHORT).show()
            }
        }

        btnIconList.setOnClickListener { showIconPicker() }
        btnTransparent.setOnClickListener { showTransparentDialog() }
        btnEmoji.setOnClickListener { showEmojiDialog() }
        btnSystem.setOnClickListener { showSystemIconPicker() }
        btnImage.setOnClickListener { showImageDialog() }
    }

    private fun refreshTile(cls: Class<*>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            TileService.requestListeningState(this, ComponentName(this, cls))
        }
    }

    private fun showScalePicker(onPick: (IconHelper.Scale) -> Unit) {
        val scales = arrayOf("小 (30%)", "中 (60%)", "大 (85%)", "满铺 (100%)")
        AlertDialog.Builder(this)
            .setTitle("选择内容占比")
            .setItems(scales) { _, which ->
                val scale = when (which) {
                    0 -> IconHelper.Scale.SMALL
                    1 -> IconHelper.Scale.MEDIUM
                    2 -> IconHelper.Scale.LARGE
                    else -> IconHelper.Scale.FULL
                }
                onPick(scale)
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun showIconPicker() {
        val icons = listOf(
            // 基础形状
            "circle" to "圆形", "square" to "方形", "triangle" to "三角",
            "cross" to "十字", "star" to "星形", "diamond" to "菱形",
            "hexagon" to "六边形", "pentagon" to "五边形",
            // 功能
            "search" to "搜索", "home" to "主页", "settings" to "设置",
            "play" to "播放", "pause" to "暂停", "mail" to "邮件",
            "link" to "链接", "user" to "用户", "lock" to "锁",
            "key" to "钥匙", "bell" to "铃铛", "heart" to "心形",
            "arrow_right" to "右箭头", "arrow_up" to "上箭头",
            "arrow_down" to "下箭头", "arrow_left" to "左箭头",
            "check" to "对勾", "close" to "叉号", "menu" to "菜单",
            "refresh" to "刷新", "download" to "下载", "upload" to "上传",
            "wifi" to "WiFi", "battery" to "电池", "location" to "定位",
            "flag" to "旗帜", "bookmark" to "书签", "calendar" to "日历",
            "clock" to "时钟", "camera" to "相机", "music" to "音乐",
            "folder" to "文件夹", "file" to "文件", "trash" to "垃圾桶",
            "eye" to "眼睛", "moon" to "月亮", "sun" to "太阳",
            "cloud" to "云", "fire" to "火焰", "bolt" to "闪电",
            // 新增
            "earth" to "地球", "car" to "小车", "rocket" to "火箭",
            "zashboard" to "Zashboard", "apple_home" to "Apple Home",
            "ha_home" to "HA智能家居",
        )
        val names = icons.map { "${it.second} (${it.first})" }.toTypedArray()
        AlertDialog.Builder(this)
            .setTitle("选择内置图标 (50+)")
            .setItems(names) { _, which ->
                val (key, _) = icons[which]
                showScalePicker { scale ->
                    TestTileCustomIcon.customIcon = key
                    TestTileCustomIcon.customScale = scale
                    refreshTile(TestTileCustomIcon::class.java)
                    Toast.makeText(this, "图标: $key ${scale.name}", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun showTransparentDialog() {
        val input = EditText(this)
        input.hint = "输入1-3个字符"
        input.maxLines = 1
        AlertDialog.Builder(this)
            .setTitle("自定义透图文字")
            .setView(input)
            .setPositiveButton("下一步") { _, _ ->
                val text = input.text.toString().trim().take(3)
                if (text.isNotEmpty()) {
                    showScalePicker { scale ->
                        TestTileCustomTransparent.customText = text
                        TestTileCustomTransparent.customScale = scale
                        refreshTile(TestTileCustomTransparent::class.java)
                        Toast.makeText(this, "透图: '$text' ${scale.name}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun showEmojiDialog() {
        val input = EditText(this)
        input.hint = "输入Emoji，如: 🐱 🔥 🇨🇳"
        input.maxLines = 1
        AlertDialog.Builder(this)
            .setTitle("自定义 Emoji")
            .setView(input)
            .setPositiveButton("下一步") { _, _ ->
                val emoji = input.text.toString().trim()
                if (emoji.isNotEmpty()) {
                    showScalePicker { scale ->
                        TestTileCustomEmoji.customEmoji = emoji
                        TestTileCustomEmoji.customScale = scale
                        refreshTile(TestTileCustomEmoji::class.java)
                        Toast.makeText(this, "Emoji: '$emoji' ${scale.name}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun showImageDialog() {
        val options = arrayOf("选择图片", "清除图片")
        AlertDialog.Builder(this)
            .setTitle("自定义图片")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> pickImageLauncher.launch("image/*")
                    1 -> {
                        TestTileCustomImage.customPath = ""
                        refreshTile(TestTileCustomImage::class.java)
                        Toast.makeText(this, "图片已清除", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun showSystemIconPicker() {
        val icons = listOf(
            "ic_menu_preferences" to android.R.drawable.ic_menu_preferences,
            "ic_menu_search" to android.R.drawable.ic_menu_search,
            "ic_menu_share" to android.R.drawable.ic_menu_share,
            "ic_menu_save" to android.R.drawable.ic_menu_save,
            "ic_menu_delete" to android.R.drawable.ic_menu_delete,
            "ic_menu_edit" to android.R.drawable.ic_menu_edit,
            "ic_menu_camera" to android.R.drawable.ic_menu_camera,
            "ic_menu_call" to android.R.drawable.ic_menu_call,
            "ic_menu_send" to android.R.drawable.ic_menu_send,
            "ic_menu_help" to android.R.drawable.ic_menu_help,
            "ic_menu_add" to android.R.drawable.ic_menu_add,
            "ic_menu_view" to android.R.drawable.ic_menu_view,
            "ic_menu_directions" to android.R.drawable.ic_menu_directions,
            "ic_menu_mylocation" to android.R.drawable.ic_menu_mylocation,
            "ic_menu_manage" to android.R.drawable.ic_menu_manage,
        )
        val names = icons.map { it.first }.toTypedArray()
        AlertDialog.Builder(this)
            .setTitle("选择系统图标")
            .setItems(names) { _, which ->
                val (name, resId) = icons[which]
                TestTileCustomSystem.customResId = resId
                refreshTile(TestTileCustomSystem::class.java)
                Toast.makeText(this, "系统图标: $name", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("取消", null)
            .show()
    }
}
