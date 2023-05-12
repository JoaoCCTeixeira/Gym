package pt.ipg.projecto

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test

    private fun getAppContext(): Context? =
        InstrumentationRegistry.getInstrumentation().targetContext

    fun apagaBaseDados(){
        getAppContext().deleteDatabase(BDGymOpenHelper.NOME_BASE_DADOS)
    }

    fun consegueAbrirBaseDados(){
        val openHelper = BDGymOpenHelper(getAppContext())
        val bd = openHelper.readableDatabase
        assert(bd.isOpen)
    }

    fun consegueInserirCategorias(){
        val openHelper = BDGymOpenHelper(getAppContext())
        val bd = openHelper.writableDatabase

        val categorias = Categoria("Drama")
        TabelaCategorias(bd).inser(categorias.toContentValues())
        assertNotEquals(-1, id)
    }

}