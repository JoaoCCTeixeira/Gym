package pt.ipg.projecto

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class PersonalTrainer (
    var nomeP: String,
    var id: Long = -1

        ){

fun toContentValues(): ContentValues{
    val valores = ContentValues()

    valores.put(TabelaPersonalTrainers.CAMPO_NOMEP, nomeP)

    return valores 
}
    companion object {
        fun fromCursor(cursor: Cursor) : PersonalTrainer {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNomeP = cursor.getColumnIndex(TabelaPersonalTrainers.CAMPO_NOMEP)

            val id = cursor.getLong(posId)
            val nomeP = cursor.getString(posNomeP)

            return PersonalTrainer(nomeP, id)
        }
    }
}