package pt.ipg.projecto

import android.content.ContentValues

data class Cliente (
    var titulo: String,
    var idCategoria: Int,
    var isbn: String? = null,
    var id: Long = -1
        ) {

    fun toContentValues(): ContentValues{
        val valores = ContentValues()

        valores.put(TabelaClientes.CAMPO_TITULO, titulo)
        valores.put(TabelaClientes.CAMPO_ISBN, isbn)
        valores.put(TabelaClientes.CAMPO_FK_CATEGORIA, idCategoria)

        return valores
    }

}