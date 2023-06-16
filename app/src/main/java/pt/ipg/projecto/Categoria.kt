package pt.ipg.projecto

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Categoria (
    var nomeP: String,
    var id: Long = -1

        ){

fun toContentValues(): ContentValues{
    val valores = ContentValues()

    valores.put(TabelaCategorias.CAMPO_NOMEP, nomeP)

    return valores 
}
    companion object {
        fun fromCursor(cursor: Cursor) : Categoria {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNomeP = cursor.getColumnIndex(TabelaCategorias.CAMPO_NOMEP)

            val id = cursor.getLong(posId)
            val nomeP = cursor.getString(posNomeP)

            return Categoria(nomeP, id)
        }
    }
}