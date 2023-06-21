package pt.ipg.projecto

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaClientes(db: SQLiteDatabase): TabelaBD(db, NOME_TABELA){
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_NOMEC TEXT NOT NULL, $CAMPO_CC TEXT, $CAMPO_DATA_NASCIMENTO INTEGER, $CAMPO_FK_PERSONALTRAINER INTEGER NOT NULL UNIQUE, FOREIGN KEY($CAMPO_FK_PERSONALTRAINER) REFERENCES ${TabelaPersonalTrainers.NOME_TABELA}(${BaseColumns._ID}) ON DELETE RESTRICT)")
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
        sql.tables = "$NOME_TABELA INNER JOIN ${TabelaPersonalTrainers.NOME_TABELA} ON ${TabelaPersonalTrainers.CAMPO_ID}=$CAMPO_FK_PERSONALTRAINER"

        return sql.query(db, colunas, selecao, argsSelecao, groupby, having, orderby)
    }

    companion object{
        const val NOME_TABELA = "clientes"

        const val CAMPO_ID = "$NOME_TABELA.${BaseColumns._ID}"
        const val CAMPO_NOMEC = "nome_c"
        const val CAMPO_CC = "cc"
        const val CAMPO_DATA_NASCIMENTO = "data_nascimento"
        const val CAMPO_FK_PERSONALTRAINER = "id_personaltrainer"
        const val CAMPO_NOMEP_PERSONALTRAINER = TabelaPersonalTrainers.CAMPO_NOMEP

        val CAMPOS = arrayOf(CAMPO_ID, CAMPO_NOMEC, CAMPO_CC, CAMPO_DATA_NASCIMENTO, CAMPO_FK_PERSONALTRAINER, CAMPO_NOMEP_PERSONALTRAINER)
    }
}
