package pt.ipg.projecto

import android.database.sqlite.SQLiteDatabase
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4


import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BDInstrumentedTest {

    fun consegueInserirLivros(){
        val bd = getWritableDatabase()

        val categoria = Categoria("humor")
        InsereCategoria(bd, categoria)

        val cliente1 = Cliente("O Lixo na Minha Cabeça",categoria.id)
        InsereLivro(bd, cliente1)

        val cliente2 =Cliente("Novíssimas crónicas da boca do inferno",categoria.id," 9789896711788")
        InsereLivro(bd, cliente2)
    }

    fun consegueInserirVenda(){
        val bd = getWritableDatabase()

        val categoria = Categoria("Drama")
        InsereCategoria(bd, categoria)
    }

    private fun InsereCategoria(
        bd: SQLiteDatabase,
        categoria: Categoria
    ) {
        TabelaCategorias(bd).inser(categoria.toContentValues())
        assertNotEquals(-1, categoria.id)
    }

    private fun InsereLivro(bd: SQLiteDatabase, livro: Cliente) {
        TabelaClientes(bd).inser(livro.toContentValues())
        assertNotEquals(-1, livro.id)
    }