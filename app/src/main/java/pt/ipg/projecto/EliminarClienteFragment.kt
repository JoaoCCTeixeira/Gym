package pt.ipg.projecto

import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.projecto.databinding.FragmentEliminarClienteBinding

class EliminarClienteFragment : Fragment() {
    private lateinit var cliente: Cliente
    private var _binding: FragmentEliminarClienteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEliminarClienteBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_eliminar

        cliente = EliminarClienteFragmentArgs.fromBundle(requireArguments()).cliente

        binding.textViewNomeC.text = cliente.nomeC
        binding.textViewCC.text = cliente.cc
        binding.textViewPersonalTrainer.text = cliente.personalTrainer.nomeP
        if (cliente.dataNascimento != null) {
            binding.textViewDataNascimento.text = DateFormat.format("yyyy-MM-dd", cliente.dataNascimento)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_eliminar -> {
                eliminar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaClientes()
                true
            }
            else -> false
        }
    }

    private fun voltaListaClientes() {
        findNavController().navigate(R.id.action_eliminarClienteFragment_to_ListaClientesFragment)
    }

    private fun eliminar() {
        val enderecoCliente = Uri.withAppendedPath(ClientesContentProvider.ENDERECO_CLIENTES, cliente.id.toString())
        val numClientesEliminados = requireActivity().contentResolver.delete(enderecoCliente, null, null)

        if (numClientesEliminados == 1) {
            Toast.makeText(requireContext(), getString(R.string.cliente_eliminado_com_sucesso), Toast.LENGTH_LONG).show()
            voltaListaClientes()
        } else {
            Snackbar.make(binding.textViewNomeC, getString(R.string.erro_eliminar_cliente), Snackbar.LENGTH_INDEFINITE)
        }
    }
}