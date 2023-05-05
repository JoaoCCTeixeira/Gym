package pt.ipg.projecto

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val NOME_BASE_DADOS = "Gym.db"
private const val VERSAO = 1

class BDGymOpenHelper(
    context: Context?,
    factory: SQLiteDatabase.CursorFactory?
) : SQLiteOpenHelper(context, NOME_BASE_DADOS, factory, VERSAO) {

    override fun onCreate(db: SQLiteDatabase?) {
        requireNotNull(db)
        TabelaCategorias(db).cria()
        TabelaClientes(db).cria()
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        if (p1 < 2) {
            TODO("Not yet implemented")
        }

    }
}