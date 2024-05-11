package hugsmaker.com.noteapp.data.local.converters

import androidx.room.TypeConverter
import java.util.Date

/**
 * Converts Date objects to Long timestamps and vice versa for Room database.
 */
class DateConverter {
    /**
     * Converts a Long value (timestamp) to a Date object.
     *
     * @param date The Long value representing a timestamp.
     * @return Date object corresponding to the timestamp, or null if the input is null.
     */
    @TypeConverter
    fun toDate(date: Long?): Date? {
        return date?.let { Date(it) }
    }

    /**
     * Converts a Date object to a Long value (timestamp).
     *
     * @param date The Date object to convert.
     * @return Long timestamp representing the Date, or null if the input is null.
     */
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}
