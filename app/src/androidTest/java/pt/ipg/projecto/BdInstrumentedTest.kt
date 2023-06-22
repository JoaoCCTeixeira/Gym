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
        //getAppContext().deleteDatabase(BDGymOpenHelper.NOME_BASE_DADOS)
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
    fun consegueInserirPersonalTrainers() {
        val bd = getWritableDatabase()

        val personalTrainer = PersonalTrainer("Nuno")
        inserePersonalTrainer(bd, personalTrainer)
    }

    private fun inserePersonalTrainer(bd: SQLiteDatabase, personalTrainer: PersonalTrainer) {
        personalTrainer.id = TabelaPersonalTrainers(bd).inser(personalTrainer.toContentValues())
        assertNotEquals(-1, personalTrainer.id)
    }


    fun consegueInserirClientes(){
        val bd = getWritableDatabase()

        val personalTrainer = PersonalTrainer("Daniel")
        inserePersonalTrainer(bd, personalTrainer)

        val cliente1 = Cliente("Rodrigo", personalTrainer)
        insereCliente(bd, cliente1)

        val cliente2 =Cliente("Ana",personalTrainer,"15727882")
        insereCliente(bd, cliente2)
    }

    private fun insereCliente(bd: SQLiteDatabase, cliente: Cliente) {
        cliente.id = TabelaClientes(bd).inser(cliente.toContentValues())
        assertNotEquals(-1, cliente.id)

    }

    @Test
    fun consegueLerPersonalTrainers() {
        val bd = getWritableDatabase()

        val ptJorge = PersonalTrainer("Jorge")
        inserePersonalTrainer(bd, ptJorge)

        val ptRonaldo = PersonalTrainer("Ronaldo")
        inserePersonalTrainer(bd, ptRonaldo)

        val tabelaPersonalTrainers = TabelaPersonalTrainers(bd)

        val cursor = tabelaPersonalTrainers.consulta(
            TabelaPersonalTrainers.CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(ptRonaldo.id.toString()),
            null,
            null,
            null
        )

        assert(cursor.moveToNext())

        val ptBD = PersonalTrainer.fromCursor(cursor)

        assertEquals(ptRonaldo, ptBD)

        val cursorTodasPersonalTrainers = tabelaPersonalTrainers.consulta(
            TabelaPersonalTrainers.CAMPOS,
            null, null, null, null,
            TabelaPersonalTrainers.CAMPO_NOMEP
        )

        assert(cursorTodasPersonalTrainers.count > 1)
    }

    @Test
    fun consegueLerClientes() {
        val bd = getWritableDatabase()

        val personalTrainer = PersonalTrainer("Hugo")
        inserePersonalTrainer(bd, personalTrainer)

        val cliente1 = Cliente("Sofia", personalTrainer)
        insereCliente(bd, cliente1)

        val dataNascimento = Calendar.getInstance()
        dataNascimento.set(2000, 4, 1)

        val cliente2 = Cliente("Joana", personalTrainer, "13780346", dataNascimento)
        insereCliente(bd, cliente2)

        val tabelaClientes = TabelaClientes(bd)

        val cursor = tabelaClientes.consulta(
            TabelaClientes.CAMPOS,
            "${TabelaClientes.CAMPO_ID}=?",
            arrayOf(cliente1.id.toString()),
            null,
            null,
            null
        )

        assert(cursor.moveToNext())

        val clienteBD = Cliente.fromCursor(cursor)

        assertEquals(cliente1, clienteBD)

        val cursorTodosClientes = tabelaClientes.consulta(
            TabelaClientes.CAMPOS,
            null, null, null, null,
            TabelaClientes.CAMPO_NOMEC
        )

        assert(cursorTodosClientes.count > 1)
    }

    @Test
    fun consegueAlterarPersonalTrainers() {
        val bd = getWritableDatabase()

        val personalTrainer = PersonalTrainer("...")
        inserePersonalTrainer(bd, personalTrainer)

        personalTrainer.nomeP = "Telmo"

        val registosAlterados = TabelaPersonalTrainers(bd).altera(
            personalTrainer.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(personalTrainer.id.toString())
        )

        assertEquals(1, registosAlterados)
    }

    @Test
    fun consegueAlterarClientes() {
        val bd = getWritableDatabase()

        val personalTrainerGoncalo = PersonalTrainer("Gonçalo")
        inserePersonalTrainer(bd, personalTrainerGoncalo)

        val personalTrainerFilipa = PersonalTrainer("Filipa")
        inserePersonalTrainer(bd, personalTrainerFilipa)

        val cliente = Cliente("...", personalTrainerFilipa)
        insereCliente(bd, cliente)

        val novaDataNascimento = Calendar.getInstance()
        novaDataNascimento.set(1968, 1, 1)

        cliente.personalTrainer = personalTrainerGoncalo
        cliente.nomeC = "Rui"
        cliente.dataNascimento = novaDataNascimento
        cliente.cc = "46817093"

        val registosAlterados = TabelaClientes(bd).altera(
            cliente.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(cliente.id.toString())
        )

        assertEquals(1, registosAlterados)
    }

    @Test
    fun consegueApagarPersonalTrainers() {
        val bd = getWritableDatabase()

        val personalTrainer = PersonalTrainer("...")
        inserePersonalTrainer(bd, personalTrainer)

        val registosEliminados = TabelaPersonalTrainers(bd).elimina(
            "${BaseColumns._ID}=?",
            arrayOf(personalTrainer.id.toString())
        )

        assertEquals(1, registosEliminados)
    }

    @Test
    fun consegueApagarClientes() {
        val bd = getWritableDatabase()

        val personalTrainer = PersonalTrainer("Pedro")
        inserePersonalTrainer(bd, personalTrainer)

        val cliente = Cliente("...", personalTrainer)
        insereCliente(bd, cliente)

        val registosEliminados = TabelaClientes(bd).elimina(
            "${BaseColumns._ID}=?",
            arrayOf(cliente.id.toString())
        )

        assertEquals(1, registosEliminados)
    }
}