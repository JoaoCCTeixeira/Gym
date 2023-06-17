package pt.ipg.projecto

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaPersonalTrainers(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_NOMEP TEXT NOT NULL)")
    }

    companion object{
        const val NOME_TABELA = "personal_trainers"

        const val CAMPO_ID = "$NOME_TABELA.${BaseColumns._ID}"
        const val CAMPO_NOMEP = "nome_p"

        val CAMPOS = arrayOf(BaseColumns._ID, CAMPO_NOMEP)
    }
}