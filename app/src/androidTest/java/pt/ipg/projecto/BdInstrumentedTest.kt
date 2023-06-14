package pt.ipg.projecto

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4


import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.util.Calendar

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BdInstrumentedTest {
    private fun getAppContext(): Context =
        InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun apagaBaseDados() {
        getAppContext().deleteDatabase(BDGymOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val openHelper = BDGymOpenHelper(getAppContext())
        val bd = openHelper.readableDatabase
        assert(bd.isOpen)
    }

    private fun getWritableDatabase(): SQLiteDatabase {
        val openHelper = BDGymOpenHelper(getAppContext())
        return openHelper.writableDatabase
    }

    @Test
    fun consegueInserirCategorias() {
        val bd = getWritableDatabase()

        val categoria = Categoria("Drama")
        insereCategoria(bd, categoria)
    }

    private fun insereCategoria(
        bd: SQLiteDatabase,
        categoria: Categoria
    ) {
        categoria.id = TabelaCategorias(bd).inser(categoria.toContentValues())
        assertNotEquals(-1, categoria.id)
    }


    fun consegueInserirClientes(){
        val bd = getWritableDatabase()

        val categoria = Categoria("humor")
        insereCategoria(bd, categoria)

        val cliente1 = Cliente("O Lixo na Minha Cabeça", categoria)
        insereCliente(bd, cliente1)

        val cliente2 =Cliente("Novíssimas crónicas da boca do inferno",categoria," 9789896711788")
        insereCliente(bd, cliente2)
    }

    private fun insereCliente(bd: SQLiteDatabase, cliente: Cliente) {
        cliente.id = TabelaClientes(bd).inser(cliente.toContentValues())
        assertNotEquals(-1, cliente.id)
    }

    @Test
    fun consegueLerCategorias() {
        val bd = getWritableDatabase()

        val categRomance = Categoria("Romance")
        insereCategoria(bd, categRomance)

        val categFiccao = Categoria("Ficção Científica")
        insereCategoria(bd, categFiccao)

        val tabelaCategorias = TabelaCategorias(bd)

        val cursor = tabelaCategorias.consulta(
            TabelaCategorias.CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(categFiccao.id.toString()),
            null,
            null,
            null
        )

        assert(cursor.moveToNext())

        val categBD = Categoria.fromCursor(cursor)

        assertEquals(categFiccao, categBD)

        val cursorTodasCategorias = tabelaCategorias.consulta(
            TabelaCategorias.CAMPOS,
            null, null, null, null,
            TabelaCategorias.CAMPO_DESCRICAO
        )

        assert(cursorTodasCategorias.count > 1)
    }

    @Test
    fun consegueLerClientes() {
        val bd = getWritableDatabase()

        val categoria = Categoria("Contos")
        insereCategoria(bd, categoria)

        val livro1 = Cliente("Todos os Contos", categoria)
        insereCliente(bd, livro1)

        val dataPub = Calendar.getInstance()
        dataPub.set(2016, 4, 1)

        val livro2 = Cliente("Contos de Grimm", categoria, "978-1473683556", dataPub)
        insereCliente(bd, livro2)

        val tabelaLivros = TabelaClientes(bd)

        val cursor = tabelaLivros.consulta(
            TabelaClientes.CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(livro1.id.toString()),
            null,
            null,
            null
        )

        assert(cursor.moveToNext())

        val livroBD = Cliente.fromCursor(cursor)

        assertEquals(livro1, livroBD)

        val cursorTodosLivros = tabelaLivros.consulta(
            TabelaClientes.CAMPOS,
            null, null, null, null,
            TabelaClientes.CAMPO_TITULO
        )

        assert(cursorTodosLivros.count > 1)
    }

    @Test
    fun consegueAlterarCategorias() {
        val bd = getWritableDatabase()

        val categoria = Categoria("...")
        insereCategoria(bd, categoria)

        categoria.descricao = "Poesia"

        val registosAlterados = TabelaCategorias(bd).altera(
            categoria.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(categoria.id.toString())
        )

        assertEquals(1, registosAlterados)
    }

    @Test
    fun consegueAlterarLivros() {
        val bd = getWritableDatabase()

        val categoriaJuvenil = Categoria("Literatura Infanto-juvenil")
        insereCategoria(bd, categoriaJuvenil)

        val categoriaNacional = Categoria("Literatura nacional")
        insereCategoria(bd, categoriaNacional)

        val livro = Cliente("...", categoriaNacional)
        insereCliente(bd, livro)

        val novaDataPub = Calendar.getInstance()
        novaDataPub.set(1968, 1, 1)

        livro.categoria = categoriaJuvenil
        livro.titulo = "Meu Pé de Laranja Lima"
        livro.dataPublicacao = novaDataPub
        livro.isbn = "978-972-8202-29-3"

        val registosAlterados = TabelaClientes(bd).altera(
            livro.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(livro.id.toString())
        )

        assertEquals(1, registosAlterados)
    }

    @Test
    fun consegueApagarCategorias() {
        val bd = getWritableDatabase()

        val categoria = Categoria("...")
        insereCategoria(bd, categoria)

        val registosEliminados = TabelaCategorias(bd).elimina(
            "${BaseColumns._ID}=?",
            arrayOf(categoria.id.toString())
        )

        assertEquals(1, registosEliminados)
    }

    @Test
    fun consegueApagarLivros() {
        val bd = getWritableDatabase()

        val categoria = Categoria("Programação")
        insereCategoria(bd, categoria)

        val livro = Cliente("...", categoria)
        insereCliente(bd, livro)

        val registosEliminados = TabelaClientes(bd).elimina(
            "${BaseColumns._ID}=?",
            arrayOf(livro.id.toString())
        )

        assertEquals(1, registosEliminados)
    }
}