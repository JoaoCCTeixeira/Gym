package pt.ipg.projecto

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

private const val NOME_TABELA = "categorias"

class TabelaCategorias(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_DESCRICAO TEXT NOT NULL)")
    }

    companion object{
        const val NOME_TABELA = "Categorias"
        const val CAMPO_DESCRICAO = "descricao"

        val CAMPOS = arrayOf(BaseColumns._ID, CAMPO_DESCRICAO)
    }
}