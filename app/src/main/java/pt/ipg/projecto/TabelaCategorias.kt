package pt.ipg.projecto

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

private const val NOME_TABELA = "categorias"

class TabelaCategorias(db: SQLiteDatabase) : TabelaBD(db, "categorias") {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, descricao TEXT NOT NULL)")
    }

    companion object{
        const val NOME_TABELA = "Categorias"
    }
}