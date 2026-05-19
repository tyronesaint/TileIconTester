package com.example.tiletester

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log

class TestTileCustomImage : TileService() {
    companion object {
        var customPath: String = ""
    }

    override fun onStartListening() {
        super.onStartListening()
        qsTile?.label = "图片"
        qsTile?.state = Tile.STATE_INACTIVE
        qsTile?.icon = if (customPath.isNotEmpty()) {
            IconHelper.customImageIcon(this, customPath)
        } else {
            IconHelper.transparentBitmap(this, "?", IconHelper.Scale.MEDIUM)
        }
        qsTile?.updateTile()
        Log.d("TileTest", "Image: path='$customPath'")
    }
    override fun onClick() {}
}
