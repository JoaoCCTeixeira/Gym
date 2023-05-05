package pt.ipg.projecto

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

abstract class TabelaBD(val db: SQLiteDatabase, val nome: String) {
    abstract fun cria()

    fun inser(valores: ContentValues) =
        db.insert(nome,  null, valores)


    fun consulta(
        colunas: Array<String>,
        selecao: String?,
        argsSelecao: Array<String>?,
        gropby: String?,
        having: String?,
        orderby: String?
    ) :Cursor = db.query(nome, colunas, selecao, argsSelecao, gropby, having, orderby)


    fun altera(valores: ContentValues, where: String, argsWhere: Array<String>) =
        db.update(nome, valores, where, argsWhere)



    fun elimine(where: String, argsWhere: Array<String>) =
        db.delete(nome, where, argsWhere)

    companion object{
        const val CHAVE_TABELA = "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT"
    }

}