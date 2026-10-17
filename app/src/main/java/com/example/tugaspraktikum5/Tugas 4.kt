package com.example.pertemuan5

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.example.tugaspraktikum5.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


@Composable
fun FormBiodataScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Scaffold sebagai lapisan bawah (berisi gambar)
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = colorResource(R.color.bg_dark)
        ) { innerPadding ->
            Image(
                painter = painterResource(R.drawable.bg_header),
                contentDescription = stringResource(R.string.cd_background),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(bottom = innerPadding.calculateBottomPadding())
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.tinggi_header))
            )
        }
        // Box form mengapung di atas Scaffold
        FormCard(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormCard(modifier: Modifier = Modifier) {
    var namaDepan by remember { mutableStateOf("") }
    var namaBelakang by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var tglLahir by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }

    val skillIds = listOf(R.string.skill_android, R.string.skill_uiux, R.string.skill_web)
    val skillLabels = skillIds.map { stringResource(it) }
    val skillChecked = remember { mutableStateListOf(false, false, false) }

    var showDatePicker by remember { mutableStateOf(false) }
    var showDropdown by remember { mutableStateOf(false) }
    var showResult by remember { mutableStateOf(false) }

    val opsiExperience = stringArrayResource(R.array.experience_list)
    val polaTanggal = stringResource(R.string.format_tanggal)

    val formValid = namaDepan.isNotBlank() &&
            namaBelakang.isNotBlank() &&
            email.isNotBlank() &&
            tglLahir.isNotEmpty() &&
            experience.isNotEmpty() &&
            skillChecked.any { it }

    val radiusCard = dimensionResource(R.dimen.radius_card)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = radiusCard, topEnd = radiusCard))
            .background(colorResource(R.color.putih))
            .navigationBarsPadding()
            .padding(dimensionResource(R.dimen.padding_card))
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.form_title),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.ungu)
            )
            Text(
                text = stringResource(R.string.form_subtitle),
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(R.color.abu_teks),
                modifier = Modifier.padding(vertical = dimensionResource(R.dimen.jarak_kecil))
            )
            Spacer(Modifier.height(dimensionResource(R.dimen.jarak)))

            // Nama Depan + Nama Belakang
            Row(modifier = Modifier.fillMaxWidth()) {
                InputField(
                    value = namaDepan,
                    onChange = { namaDepan = it },
                    label = stringResource(R.string.nama_depan),
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(dimensionResource(R.dimen.jarak_kecil)))
                InputField(
                    value = namaBelakang,
                    onChange = { namaBelakang = it },
                    label = stringResource(R.string.nama_belakang),
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(dimensionResource(R.dimen.jarak)))

            // Email
            InputField(
                value = email,
                onChange = { email = it },
                label = stringResource(R.string.email),
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Email
            )
            Spacer(Modifier.height(dimensionResource(R.dimen.jarak)))

            // Tgl Lahir + Experience
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.weight(1f)) {
                    FieldKlik(
                        value = tglLahir,
                        label = stringResource(R.string.tgl_lahir),
                        icon = R.drawable.ic_dropdown,
                        iconDesc = stringResource(R.string.cd_kalender),
                        onClick = { showDatePicker = true }
                    )
                }
                Spacer(Modifier.width(dimensionResource(R.dimen.jarak_kecil)))
                Box(modifier = Modifier.weight(1f)) {
                    FieldKlik(
                        value = experience,
                        label = stringResource(R.string.experience),
                        icon = R.drawable.ic_kalender,
                        iconDesc = stringResource(R.string.cd_dropdown),
                        onClick = { showDropdown = true }
                    )
                    DropdownMenu(
                        expanded = showDropdown,
                        onDismissRequest = { showDropdown = false }
                    ) {
                        opsiExperience.forEach { opsi ->
                            DropdownMenuItem(
                                text = { Text(opsi) },
                                onClick = {
                                    experience = opsi
                                    showDropdown = false
                                }
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(dimensionResource(R.dimen.jarak)))

            // Skill
            Text(
                text = stringResource(R.string.pilih_skill),
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.hitam),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                skillIds.forEachIndexed { index, id ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { skillChecked[index] = !skillChecked[index] }
                    ) {
                        Checkbox(
                            checked = skillChecked[index],
                            onCheckedChange = { skillChecked[index] = it }
                        )
                        Text(
                            text = stringResource(id),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
            Spacer(Modifier.height(dimensionResource(R.dimen.jarak)))

            // Tombol Proceed
            Button(
                onClick = { showResult = true },
                enabled = formValid,
                shape = RoundedCornerShape(dimensionResource(R.dimen.radius_field)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.ungu),
                    contentColor = colorResource(R.color.putih),
                    disabledContainerColor = colorResource(R.color.tombol_disabled),
                    disabledContentColor = colorResource(R.color.putih)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.tinggi_tombol))
            ) {
                Text(stringResource(R.string.proceed))
            }
        }
    }

    // Dialog pilih tanggal
    if (showDatePicker) {
        val dateState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    dateState.selectedDateMillis?.let { millis ->
                        val formatter = SimpleDateFormat(polaTanggal, Locale.getDefault())
                        formatter.timeZone = TimeZone.getTimeZone("UTC")
                        tglLahir = formatter.format(Date(millis))
                    }
                    showDatePicker = false
                }) { Text(stringResource(R.string.ok)) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(stringResource(R.string.batal))
                }
            }
        ) {
            DatePicker(state = dateState)
        }
    }

    // Dialog hasil
    if (showResult) {
        val skillTerpilih = skillLabels
            .filterIndexed { index, _ -> skillChecked[index] }
            .joinToString()
        AlertDialog(
            onDismissRequest = { showResult = false },
            title = { Text(stringResource(R.string.hasil_title)) },
            text = {
                Text(
                    stringResource(
                        R.string.hasil_isi,
                        "$namaDepan $namaBelakang",
                        email,
                        tglLahir,
                        experience,
                        skillTerpilih
                    )
                )
            },
            confirmButton = {
                TextButton(onClick = { showResult = false }) {
                    Text(stringResource(R.string.ok))
                }
            }
        )
    }
}

class Icons(value: String, label: String, icon: Any, iconDesc: String, onClick: () -> Unit) {
    class Default(value: String, label: String, icon: Any, iconDesc: String, onClick: () -> Unit) {

    }

}

@Composable
fun InputField(
    value: String,
    onChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        singleLine = true,
        label = { Text(label) },
        shape = RoundedCornerShape(dimensionResource(R.dimen.radius_field)),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(R.color.ungu),
            unfocusedBorderColor = colorResource(R.color.border_abu)
        ),
        modifier = modifier
    )
}

