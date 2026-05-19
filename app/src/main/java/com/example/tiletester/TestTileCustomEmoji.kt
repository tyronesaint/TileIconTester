package com.example.tiletester

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log

class TestTileCustomEmoji : TileService() {
    companion object {
        var customEmoji: String = "🚀"
        var customScale: IconHelper.Scale = IconHelper.Scale.MEDIUM
    }

    override fun onStartListening() {
        super.onStartListening()
        qsTile?.label = "Emoji"
        qsTile?.state = Tile.STATE_INACTIVE
        qsTile?.icon = IconHelper.emojiIcon(this, customEmoji, customScale)
        qsTile?.updateTile()
        Log.d("TileTest", "Emoji: emoji='$customEmoji' scale=$customScale")
    }
    override fun onClick() {}
}
