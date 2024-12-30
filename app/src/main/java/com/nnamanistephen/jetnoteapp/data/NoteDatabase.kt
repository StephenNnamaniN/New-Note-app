package com.nnamanistephen.jetnoteapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nnamanistephen.jetnoteapp.model.Note
import com.nnamanistephen.jetnoteapp.util.DateConverter
import com.nnamanistephen.jetnoteapp.util.UUIDConverter

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, UUIDConverter::class)
abstract  class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDatabaseDao
}