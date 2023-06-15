package pt.ipg.projecto

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable
import java.util.Calendar

data class Cliente (
    var nome: String,
    var categoria: Categoria,
    var cc: String? = null,
    var dataNascimento: Calendar? = null,
    var id: Long = -1
) : Serializable {

    fun toContentValues(): ContentValues{
        val valores = ContentValues()

        valores.put(TabelaClientes.CAMPO_NOME, nome)
        valores.put(TabelaClientes.CAMPO_CC, cc)
        valores.put(TabelaClientes.CAMPO_DATA_NASCIMENTO, dataNascimento?.timeInMillis)
        valores.put(TabelaClientes.CAMPO_FK_CATEGORIA, categoria.id)

        return valores
    }
    companion object {
        fun fromCursor(cursor: Cursor): Cliente {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNome = cursor.getColumnIndex(TabelaClientes.CAMPO_NOME)
            val posCC = cursor.getColumnIndex(TabelaClientes.CAMPO_CC)
            val posDataNascimento = cursor.getColumnIndex(TabelaClientes.CAMPO_DATA_NASCIMENTO)
            val posCategoriaFK = cursor.getColumnIndex(TabelaClientes.CAMPO_FK_CATEGORIA)
            val posDescCateg = cursor.getColumnIndex(TabelaClientes.CAMPO_DESC_CATEGORIA)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posNome)
            val cc = cursor.getString(posCC)

            var dataNascimento: Calendar?

            if (cursor.isNull(posDataNascimento)) {
                dataNascimento = null
            } else {
                dataNascimento = Calendar.getInstance()
                dataNascimento.timeInMillis = cursor.getLong(posDataNascimento)
            }

            val categoriaId = cursor.getLong(posCategoriaFK)
            val desricaoCategoria = cursor.getString(posDescCateg)

            return Cliente(nome, Categoria(desricaoCategoria, categoriaId), cc, dataNascimento, id)
        }
    }
}