package com.survivalcoding.noteapp

class Config {
    companion object {
        // dual pane
        var dualPane: Boolean = false

        // room
        const val DATABASE_NAME = "note_db"
        const val TABLE_NAME = "note_table"

        // colorCode
        const val COLOR_CODE_RED = 0
        const val COLOR_CODE_YELLOW = 1
        const val COLOR_CODE_PURPLE = 2
        const val COLOR_CODE_BLUE = 3
        const val COLOR_CODE_PINK = 4

        // orderKey
        const val ORDER_KEY_TITLE_ASC = "title_asc"
        const val ORDER_KEY_TITLE_DESC = "title_desc"
        const val ORDER_KEY_COLOR_ASC = "color_asc"
        const val ORDER_KEY_COLOR_DESC = "color_desc"
        const val ORDER_KEY_TIME_ASC = "time_asc"
        const val ORDER_KEY_TIME_DESC = "time_desc"

        // orderCode
        const val ORDER_CODE_TITLE = 0
        const val ORDER_CODE_DATE = 1
        const val ORDER_CODE_COLOR = 2

        // extraKey
        const val EXTRA_KEY_FRAGMENT = "intent_key_fragment"
        const val EXTRA_KEY_NOTE = "extra_key_note"

        // fragmentCode
        const val FRAGMENT_CODE_ADD = 0
        const val FRAGMENT_CODE_EDIT = 1

        // prefs
        const val PREFS = "prefs"
        const val PREFS_KEY_ORDER = "prefs_key_order"
    }
}