package org.techtales.notes.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import org.techtales.notes.Models.Notes
import org.techtales.notes.Utilites.DATABASE_NAME
import java.util.Date

@Database(entities = arrayOf(Notes::class), version = 1, exportSchema = false)
//@TypeConverters(DateConverter::class)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao() : NoteDao

    companion object{

        @Volatile
        private var INSTANCE : NoteDatabase? = null

        fun getDatabase(context: Context) : NoteDatabase{

            return INSTANCE ?: synchronized(this){

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    DATABASE_NAME
                ).build()

                INSTANCE = instance

                instance
            }
        }

        //fun buildDatabase(NotesContext: Context) {
           // Room.databaseBuilder(NotesContext, RoomDatabase::class.java, "note")
               // .fallbackToDestructiveMigration()
               // .build()

       // }

    }
}

//class DateConverter {

   // @TypeConverter
   // fun toDate(timestamp: Long): Date {
     //   return Date(timestamp)
   // }

   // @TypeConverter
   // fun toTimestamp(date: Date): Long {
       // return date.time
   // }

//}
