package com.survivalcoding.noteapp

class Config {
    companion object {
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

        // fragmentCode
        const val FRAGMENT_CODE_ADD = 0
        const val FRAGMENT_CODE_EDIT = 1
    }
}