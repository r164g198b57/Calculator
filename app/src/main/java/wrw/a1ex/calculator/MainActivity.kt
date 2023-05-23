package wrw.a1ex.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

val OPERATION = "OPERATION"
val OPERAND = "OPERAND"
var operand: Double? = null


class MainActivity : AppCompatActivity() {
    lateinit var lastOperation: String
    lateinit var resultField: TextView
    lateinit var numberField: EditText
    lateinit var operationField: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resultField = findViewById(R.id.resultField)
        numberField = findViewById(R.id.numberField)
        operationField = findViewById(R.id.operationField)

        if (savedInstanceState == null) {
            lastOperation = "="
            operand = null
        } else {
            lastOperation = savedInstanceState.getString(OPERATION).toString()
            operand = savedInstanceState.getDouble(OPERAND)
            resultField.setText(operand.toString())
            operationField.setText(lastOperation)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(OPERATION, lastOperation)
        if (operand != null) outState.putDouble(OPERAND, operand!!)
    }

    fun onNumberClick(view: View) {
        var button = view as Button
        numberField.append(button.text);
        if (lastOperation.equals("=") && operand != null) {
            operand = null;
        }
    }

    fun onOperationClick(view: View) {
        var button = view as Button
        var op = button.text.toString()
        var number = numberField.text.toString()
        if (number.length > 0) {
            number.replace(",", ".")

            try {
                performOperation(number.toDouble(), op)

            } catch (ex: NumberFormatException) {
                numberField.setText("")

            }
        }
        lastOperation = op
        operationField.setText(lastOperation)
    }

    fun performOperation(number: Double, operation: String) {
        if (operand == null) operand = number
        else {
            if (lastOperation.equals("=")) {
                lastOperation = operation
            }
            when (lastOperation) {
                "=" -> operand = number
                "*" -> operand = operand!! * number
                "+" -> operand = operand!! + number
                "-" -> operand = operand!! - number
                "/" -> operand = operand!! / number

            }
        }
        resultField.setText(operand.toString().replace(".", ","))
        numberField.setText("")
    }
}
