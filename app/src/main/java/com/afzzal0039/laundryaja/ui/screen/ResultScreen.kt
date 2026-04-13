package com.afzzal0039.laundryaja.ui.screen

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.afzzal0039.laundryaja.R
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    berat: String,
    paket: String,
    hasJaket: Boolean,
    hasSprei: Boolean,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    val hargaPerKg = if (paket == "Reguler") 5000 else 8000
    val biayaKiloan = (berat.toDoubleOrNull() ?: 0.0) * hargaPerKg
    val tambahanBiaya = (if (hasJaket) 10000 else 0) + (if (hasSprei) 15000 else 0)
    val total = biayaKiloan + tambahanBiaya

    val localeID = Locale.forLanguageTag("id-ID")
    val totalFormatted = String.format(localeID, "%,.0f", total)

    fun hitungEstimasi(paket: String): String {
        val calendar = Calendar.getInstance()
        if (paket == "Reguler") {
            calendar.add(Calendar.DAY_OF_YEAR, 2)
        } else {
            calendar.add(Calendar.HOUR_OF_DAY, 6)
        }
        val format = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        return format.format(calendar.time)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.hasil_hitung)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
                .padding(16.dp)
        ) {
            Text("Detail: $berat kg ($paket)", fontWeight = FontWeight.Bold)

            if (hasJaket) Text("- Termasuk Cuci Jaket (+Rp 10.000)")
            if (hasSprei) Text("- Termasuk Cuci Sprei (+Rp 15.000)")

            Spacer(modifier = Modifier.height(12.dp))

            Text("Estimasi Selesai: ${hitungEstimasi(paket)}")

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Total Biaya: Rp $totalFormatted",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val shareText = "Laundry $berat kg ($paket)\nEstimasi selesai: ${hitungEstimasi(paket)}\nTotal: Rp $totalFormatted"
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, shareText)
                        type = "text/plain"
                    }
                    context.startActivity(Intent.createChooser(sendIntent, null))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.btn_bagikan))
            }
        }
    }
}