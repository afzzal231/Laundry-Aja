package com.afzzal0039.laundryaja.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.afzzal0039.laundryaja.R
import com.afzzal0039.laundryaja.ui.theme.LaundryAjaTheme

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreview() {
    LaundryAjaTheme {
        MainScreen(
            onCalculate = { _, _, _, _ -> },
            onAboutClick = { }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onCalculate: (String, String, Boolean, Boolean) -> Unit,
    onAboutClick: () -> Unit
) {
    var berat by rememberSaveable { mutableStateOf("") }
    var paket by rememberSaveable { mutableStateOf("Reguler") }
    var isError by remember { mutableStateOf(false) }

    var hasJaket by rememberSaveable { mutableStateOf(false) }
    var hasSprei by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = onAboutClick) {
                        Icon(Icons.Default.Info, contentDescription = "About")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.laundry),
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = berat,
                onValueChange = { berat = it; isError = false },
                label = { Text(stringResource(id = R.string.label_berat)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = isError,
                modifier = Modifier.fillMaxWidth()
            )
            if (isError) {
                Text(stringResource(id = R.string.error_input), color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Item Khusus (Satuan):", fontWeight = FontWeight.Bold)

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Checkbox(checked = hasJaket, onCheckedChange = { hasJaket = it })
                Text("Jaket (+Rp 10.000)")
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Checkbox(checked = hasSprei, onCheckedChange = { hasSprei = it })
                Text("Sprei (+Rp 15.000)")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(stringResource(id = R.string.pilih_paket), fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = (paket == "Reguler"), onClick = { paket = "Reguler" })
                Text("Reguler")
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(selected = (paket == "Ekspres"), onClick = { paket = "Ekspres" })
                Text("Ekspres")
            }

            Button(
                onClick = {
                    if (berat.isEmpty() || berat.toDoubleOrNull() == null) {
                        isError = true
                    } else {
                        onCalculate(berat, paket, hasJaket, hasSprei)
                    }
                },
                modifier = Modifier
                    .padding(top = 32.dp)
                    .fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.btn_hitung))
            }
        }
    }
}