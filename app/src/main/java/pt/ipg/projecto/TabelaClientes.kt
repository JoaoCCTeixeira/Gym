package pt.ipg.projecto

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaClientes(db: SQLiteDatabase): TabelaBD(db, "Clientes"){
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA($CHAVE_TABELA, titulo TEXT NOT NULL, isbn TEXT, id_categoria INTEGER NOT NULL),FOREIGN KEY(id_categoria) REFERENCES CATEGORIAS ${TabelaCategorias.NOME_TABELA}(${BaseColumns._ID})ON DELETE RESTRICT")
    }

    companion object{
        const val NOME_TABELA = "Clientes"
    }
}
