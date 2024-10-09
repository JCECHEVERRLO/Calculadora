package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlin.math.sqrt
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   Calculadora(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Calculadora(modificador: Modifier = Modifier) {
    var pantalla by remember { mutableStateOf("0") }
    var operador by remember { mutableStateOf("") }
    var operando1 by remember { mutableStateOf<Double?>(null) }

    Column(
        modifier = modificador
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = pantalla,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth(),
            maxLines = 1
        )

        val modificadorBoton = Modifier
            .padding(8.dp)
            .weight(1f)

        // Filas de botones
        Column {
            Row {
                BotonCalculadora("7", modificadorBoton) { enDigito("7", pantalla, setPantalla = { pantalla = it }) }
                BotonCalculadora("8", modificadorBoton) { enDigito("8", pantalla, setPantalla = { pantalla = it }) }
                BotonCalculadora("9", modificadorBoton) { enDigito("9", pantalla, setPantalla = { pantalla = it }) }
                BotonCalculadora("/", modificadorBoton) { enOperador("/", pantalla, operador, operando1, setOperando1 = { operando1 = it }, setPantalla = { pantalla = it }, setOperador = { operador = it }) }
            }
            Row {
                BotonCalculadora("4", modificadorBoton) { enDigito("4", pantalla, setPantalla = { pantalla = it }) }
                BotonCalculadora("5", modificadorBoton) { enDigito("5", pantalla, setPantalla = { pantalla = it }) }
                BotonCalculadora("6", modificadorBoton) { enDigito("6", pantalla, setPantalla = { pantalla = it }) }
                BotonCalculadora("*", modificadorBoton) { enOperador("*", pantalla, operador, operando1, setOperando1 = { operando1 = it }, setPantalla = { pantalla = it }, setOperador = { operador = it }) }
            }
            Row {
                BotonCalculadora("1", modificadorBoton) { enDigito("1", pantalla, setPantalla = { pantalla = it }) }
                BotonCalculadora("2", modificadorBoton) { enDigito("2", pantalla, setPantalla = { pantalla = it }) }
                BotonCalculadora("3", modificadorBoton) { enDigito("3", pantalla, setPantalla = { pantalla = it }) }
                BotonCalculadora("-", modificadorBoton) { enOperador("-", pantalla, operador, operando1, setOperando1 = { operando1 = it }, setPantalla = { pantalla = it }, setOperador = { operador = it }) }
            }
            Row {
                BotonCalculadora("0", modificadorBoton) { enDigito("0", pantalla, setPantalla = { pantalla = it }) }
                BotonCalculadora("√", modificadorBoton) { pantalla = sqrt(pantalla.toDouble()).toString() }
                BotonCalculadora("%", modificadorBoton) { pantalla = (pantalla.toDouble() / 100).toString() }
                BotonCalculadora("+", modificadorBoton) { enOperador("+", pantalla, operador, operando1, setOperando1 = { operando1 = it }, setPantalla = { pantalla = it }, setOperador = { operador = it }) }
            }
            Row {
                BotonCalculadora("=", modificadorBoton) {
                    enIgual(pantalla, operador, operando1, setPantalla = { pantalla = it }, setOperador = { operador = it }, setOperando1 = { operando1 = it })
                }
            }
        }
    }
}

@Composable
fun BotonCalculadora(etiqueta: String, modificador: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modificador
            .size(80.dp)
    ) {
        Text(text = etiqueta, style = MaterialTheme.typography.titleLarge)
    }
}

// Función auxiliar para manejar los clics de dígitos
fun enDigito(digito: String, pantalla: String, setPantalla: (String) -> Unit) {
    if (pantalla == "0") {
        setPantalla(digito)
    } else {
        setPantalla(pantalla + digito)
    }
}

// Función auxiliar para manejar los clics de operadores
fun enOperador(
    operadorSeleccionado: String,
    pantalla: String,
    operadorActual: String,
    operando1: Double?,
    setOperando1: (Double?) -> Unit,
    setPantalla: (String) -> Unit,
    setOperador: (String) -> Unit
) {
    if (operando1 == null) {
        setOperando1(pantalla.toDouble())
        setPantalla("0")
    }
    setOperador(operadorSeleccionado)
}

// Función auxiliar para manejar el clic del botón igual
fun enIgual(
    pantalla: String,
    operador: String,
    operando1: Double?,
    setPantalla: (String) -> Unit,
    setOperador: (String) -> Unit,
    setOperando1: (Double?) -> Unit
) {
    val operando2 = pantalla.toDouble()
    if (operando1 != null && operador.isNotEmpty()) {
        val resultado = when (operador) {
            "+" -> operando1 + operando2
            "-" -> operando1 - operando2
            "*" -> operando1 * operando2
            "/" -> if (operando2 != 0.0) operando1 / operando2 else Double.NaN
            else -> 0.0
        }
        setPantalla(resultado.toString())
        setOperando1(null)
        setOperador("")
    }
}

@Preview(showBackground = true)
@Composable
fun VerCalculadora() {
    MyApplicationTheme {
      Calculadora()
    }
}