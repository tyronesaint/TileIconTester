package com.example.tiletester

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log

/** 自定义内置图标 */
class TestTileCustomIcon : TileService() {
    companion object {
        var customIcon: String = "circle"
        var customScale: IconHelper.Scale = IconHelper.Scale.MEDIUM
    }

    override fun onStartListening() {
        super.onStartListening()
        qsTile?.label = "图标"
        qsTile?.state = Tile.STATE_INACTIVE
        qsTile?.icon = IconHelper.icon(this, customIcon, customScale)
        qsTile?.updateTile()
        Log.d("TileTest", "Icon: name='$customIcon' scale=$customScale")
    }
    override fun onClick() {}
}
