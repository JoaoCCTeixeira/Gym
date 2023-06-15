package pt.ipg.projecto

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaClientes(db: SQLiteDatabase): TabelaBD(db, NOME_TABELA){
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_NOME TEXT NOT NULL, $CAMPO_CC TEXT, $CAMPO_FK_CATEGORIA INTEGER NOT NULL UNIQUE,    FOREIGN KEY($CAMPO_FK_CATEGORIA) REFERENCES ${TabelaCategorias.NOME_TABELA}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    override fun consulta(
        colunas: Array<String>,
        selecao: String?,
        argsSelecao: Array<String>?,
        groupby: String?,
        having: String?,
        orderby: String?
    ): Cursor {
        val sql = SQLiteQueryBuilder()
        sql.tables = "$NOME_TABELA INNER JOIN ${TabelaCategorias.NOME_TABELA} ON ${TabelaCategorias.CAMPO_ID}=$CAMPO_FK_CATEGORIA"

        return sql.query(db, colunas, selecao, argsSelecao, groupby, having, orderby)
    }

    companion object{
        const val NOME_TABELA = "Clientes"

        const val CAMPO_NOME = "nome"
        const val CAMPO_CC = "cc"
        const val CAMPO_DATA_PUB = "data_publicacao"
        const val CAMPO_FK_CATEGORIA = "id_categoria"
        const val CAMPO_DESC_CATEGORIA = TabelaCategorias.CAMPO_DESCRICAO

        val CAMPOS = arrayOf(BaseColumns._ID, CAMPO_NOME, CAMPO_CC, CAMPO_DATA_PUB, CAMPO_FK_CATEGORIA, CAMPO_DESC_CATEGORIA)
    }
}
