package pt.ipg.projecto

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable
import java.util.Calendar

data class Cliente (
    var nome: String,
    var categoria: Categoria,
    var isbn: String? = null,
    var dataPublicacao: Calendar? = null,
    var id: Long = -1
) : Serializable {

    fun toContentValues(): ContentValues{
        val valores = ContentValues()

        valores.put(TabelaClientes.CAMPO_NOME, nome)
        valores.put(TabelaClientes.CAMPO_ISBN, isbn)
        valores.put(TabelaClientes.CAMPO_DATA_PUB, dataPublicacao?.timeInMillis)
        valores.put(TabelaClientes.CAMPO_FK_CATEGORIA, categoria.id)

        return valores
    }
    companion object {
        fun fromCursor(cursor: Cursor): Cliente {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaClientes.CAMPO_NOME)
            val posISBN = cursor.getColumnIndex(TabelaClientes.CAMPO_ISBN)
            val posDataPub = cursor.getColumnIndex(TabelaClientes.CAMPO_DATA_PUB)
            val posCategoriaFK = cursor.getColumnIndex(TabelaClientes.CAMPO_FK_CATEGORIA)
            val posDescCateg = cursor.getColumnIndex(TabelaClientes.CAMPO_DESC_CATEGORIA)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)
            val isbn = cursor.getString(posISBN)

            var dataPub: Calendar?

            if (cursor.isNull(posDataPub)) {
                dataPub = null
            } else {
                dataPub = Calendar.getInstance()
                dataPub.timeInMillis = cursor.getLong(posDataPub)
            }

            val categoriaId = cursor.getLong(posCategoriaFK)
            val desricaoCategoria = cursor.getString(posDescCateg)

            return Cliente(nome, Categoria(desricaoCategoria, categoriaId), isbn, dataPub, id)
        }
    }
}