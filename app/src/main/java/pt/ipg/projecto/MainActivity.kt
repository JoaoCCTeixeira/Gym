package pt.ipg.projecto

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import pt.ipg.projecto.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var menu: Menu
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    var idMenuAtual : Int = R.menu.menu_main
        set(value) {
            if (value != field) {
                field = value
                invalidateOptionsMenu()
            }
        }

    var fragment : Fragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(idMenuAtual, menu)
        this.menu = menu

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_settings) {
            return true
        }

        val opcaoProcessada = when (fragment) {
            //is ListaClientesFragment -> (fragment as ListaClientesFragment).processaOpcaoMenu(item)
            is EditarClienteFragment -> (fragment as EditarClienteFragment).processaOpcaoMenu(item)
            is EliminarClienteFragment -> (fragment as EliminarClienteFragment).processaOpcaoMenu(item)
            else -> false
        }

        return if (opcaoProcessada) { true } else { super.onOptionsItemSelected(item) }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun mostraOpcaoMenu(idOpcao: Int, mostrar: Boolean) {
        menu.findItem(idOpcao).setVisible(mostrar)
    }

    fun atualizaTitulo(label: Int) = binding.toolbar.setTitle(label)
}