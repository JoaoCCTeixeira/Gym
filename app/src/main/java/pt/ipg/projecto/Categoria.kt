package pt.ipg.projecto

import android.content.ContentValues

data class Categoria (
    var descricao: String,
    var id: Long = -1

        ){

fun toContentValues(): ContentValues{
    val valores = ContentValues()

    valores.put(TabelaCategorias.CAMPO_DESCRICAO, descricao)

    return valores
}
}