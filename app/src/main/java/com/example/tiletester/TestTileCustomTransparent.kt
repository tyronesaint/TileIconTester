package com.example.tiletester

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log

class TestTileCustomTransparent : TileService() {
    companion object {
        var customText: String = "字"
        var customScale: IconHelper.Scale = IconHelper.Scale.MEDIUM
    }

    override fun onStartListening() {
        super.onStartListening()
        qsTile?.label = "透图"
        qsTile?.state = Tile.STATE_INACTIVE
        qsTile?.icon = IconHelper.transparentBitmap(this, customText, customScale)
        qsTile?.updateTile()
        Log.d("TileTest", "Transparent: text='$customText' scale=$customScale")
    }
    override fun onClick() {}
}
