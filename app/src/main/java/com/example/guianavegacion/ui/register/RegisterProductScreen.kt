package com.example.guianavegacion.ui.register

import android.widget.Toast
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.guianavegacion.data.model.ProductCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterProductScreen(
    onBackClick: () -> Unit,
    viewModel: RegisterProductViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var isDropdownExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            Toast.makeText(context, "¡Producto registrado con éxito!", Toast.LENGTH_SHORT).show()
            viewModel.resetSuccessFlag()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Producto") },
                navigationIcon = {
                    OutlinedButton(
                        onClick = onBackClick,
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text("Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Formulario de Registro",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            // Vista previa de la imagen/foto según la categoría seleccionada
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = uiState.selectedCategory.badgeColor.copy(alpha = 0.15f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(uiState.selectedCategory.badgeColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = uiState.selectedCategory.imageSymbol,
                            fontSize = 32.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Imagen por Tipo",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                        Text(
                            text = uiState.selectedCategory.displayName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = uiState.selectedCategory.badgeColor
                        )
                    }
                }
            }

            // Campo: Nombre del producto
            OutlinedTextField(
                value = uiState.name,
                onValueChange = { viewModel.onNameChange(it) },
                label = { Text("Nombre del Producto") },
                placeholder = { Text("Ej. Teléfono Samsung") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Campo: Precio del producto
            OutlinedTextField(
                value = uiState.price,
                onValueChange = { viewModel.onPriceChange(it) },
                label = { Text("Precio ($)") },
                placeholder = { Text("Ej. 299.99") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Campo: Tipo de Producto (Desplegable / Selector)
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = "${uiState.selectedCategory.imageSymbol}  ${uiState.selectedCategory.displayName}",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo / Categoría de Producto") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false,
                    colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                // Capa transparente para detectar el clic en la caja de texto
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { isDropdownExpanded = true }
                )
                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false },
                    modifier = Modifier.fillMaxWidth(0.85f)
                ) {
                    ProductCategory.entries.forEach { category ->
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = category.imageSymbol, fontSize = 20.sp)
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(text = category.displayName, fontWeight = FontWeight.Medium)
                                }
                            },
                            onClick = {
                                viewModel.onCategoryChange(category)
                                isDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            // Mensaje de error si la validación falla
            if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón para registrar el producto
            Button(
                onClick = { viewModel.registerProduct() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Registrar Producto", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
