package pt.ipg.projecto

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable
import java.util.Calendar

data class Cliente(
    var nomeC: String,
    var personalTrainer: PersonalTrainer,
    var cc: String? = null,
    var dataNascimento: Calendar? = null,
    var id: Long = -1
) : Serializable {
    fun toContentValues(): ContentValues{
        val valores = ContentValues()

        valores.put(TabelaClientes.CAMPO_NOMEC, nomeC)
        valores.put(TabelaClientes.CAMPO_CC, cc)
        valores.put(TabelaClientes.CAMPO_DATA_NASCIMENTO, dataNascimento?.timeInMillis)
        valores.put(TabelaClientes.CAMPO_FK_PERSONALTRAINER, personalTrainer.id)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Cliente {
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posNomeC = cursor.getColumnIndex(TabelaClientes.CAMPO_NOMEC)
            val posCC = cursor.getColumnIndex(TabelaClientes.CAMPO_CC)
            val posDataNascimento = cursor.getColumnIndex(TabelaClientes.CAMPO_DATA_NASCIMENTO)
            val posPersonalTrainerFK = cursor.getColumnIndex(TabelaClientes.CAMPO_FK_PERSONALTRAINER)
            val posNomePersonalTrainer = cursor.getColumnIndex(TabelaClientes.CAMPO_NOMEP_PERSONALTRAINER)

            val id = cursor.getLong(posId)
            val nomeC = cursor.getString(posNomeC)
            val cc = cursor.getString(posCC)

            var dataNascimento: Calendar?

            if (cursor.isNull(posDataNascimento)) {
                dataNascimento = null
            } else {
                dataNascimento = Calendar.getInstance()
                dataNascimento.timeInMillis = cursor.getLong(posDataNascimento)
            }

            val personalTrainerId = cursor.getLong(posPersonalTrainerFK)
            val nomePersonalTrainer = cursor.getString(posNomePersonalTrainer)

            return Cliente(nomeC, PersonalTrainer(nomePersonalTrainer, personalTrainerId), cc, dataNascimento, id)
        }
    }
}