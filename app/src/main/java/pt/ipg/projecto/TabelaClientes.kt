package pt.ipg.projecto

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaClientes(db: SQLiteDatabase): TabelaBD(db, NOME_TABELA){
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_TITULO TEXT NOT NULL, $CAMPO_ISBN TEXT, $CAMPO_FK_CATEGORIA INTEGER NOT NULL UNIQUE,    FOREIGN KEY($CAMPO_FK_CATEGORIA) REFERENCES ${TabelaCategorias.NOME_TABELA}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    companion object{
        const val NOME_TABELA = "Clientes"
        const val CAMPO_TITULO = "titulo"
        const val CAMPO_ISBN = "isbn"
        const val CAMPO_DATA_PUB = "data_publicacao"
        const val CAMPO_FK_CATEGORIA = "id_categoria"

        val CAMPOS = arrayOf(BaseColumns._ID, CAMPO_TITULO, CAMPO_ISBN, CAMPO_DATA_PUB, CAMPO_FK_CATEGORIA)
    }
}
