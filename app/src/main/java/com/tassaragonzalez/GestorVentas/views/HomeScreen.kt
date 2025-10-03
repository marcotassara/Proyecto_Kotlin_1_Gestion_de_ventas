package com.tassaragonzalez.GestorVentas.views

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tassaragonzalez.GestorVentas.R
import com.tassaragonzalez.GestorVentas.navigation.Screen
import com.tassaragonzalez.GestorVentas.ui.theme.GestorVentasTheme
import com.tassaragonzalez.GestorVentas.viewmodels.GestorVentasViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// =================================================================================
// --- SECCIÓN 1: La Pantalla Principal (Contenedor con Menú) ---
// =================================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: GestorVentasViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(viewModel = viewModel, drawerState = drawerState, scope = scope)
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("¡Bienvenido a Neg!") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menú")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        ) { innerPadding ->
            // Le pasamos la acción de navegación para la tarjeta de stock.
            HomeContent(
                innerPadding = innerPadding,
                onLowStockClick = { viewModel.navigateTo(Screen.ProductsScreen) }
            )
        }
    }
}

// =================================================================================
// --- SECCIÓN 2: El Contenido del Menú Deslizable ---
// =================================================================================
@Composable
fun DrawerContent(
    viewModel: GestorVentasViewModel,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    ModalDrawerSheet {
        Text("Menú Principal", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
        Divider()
        NavigationDrawerItem(
            label = { Text(text = "Registrar Nuevo Cliente") },
            selected = false,
            onClick = {
                scope.launch { drawerState.close() }
                viewModel.navigateTo(Screen.RegisterClientScreen)
            }
        )
        NavigationDrawerItem(
            label = { Text(text = "Productos") },
            selected = false,
            onClick = {
                scope.launch { drawerState.close() }
                viewModel.navigateTo(Screen.ProductsScreen)
            }
        )
        NavigationDrawerItem(
            label = { Text(text = "Configuraciones") },
            selected = false,
            onClick = {
                scope.launch { drawerState.close() }
                viewModel.navigateTo(Screen.SettingsScreen)
            }
        )
    }
}

// =================================================================================
// --- SECCIÓN 3: El Contenido Visual de la Pantalla (Reordenado) ---
// =================================================================================
@Composable
fun HomeContent(
    innerPadding: PaddingValues,
    onLowStockClick: () -> Unit // Recibe la acción de clic para la alerta
) {

    // 1. Creamos un "producto" especial que actúa como alerta.
    val lowStockAlert = Product(
        name = "¡ALERTA! STOCK BAJO",
        price = "Revisar productos críticos",
        imageRes = R.drawable.gestor // Puedes cambiar este ícono por uno de alerta si quieres
    )

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "REPORTE DE VENTAS CRÍTICOS",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD0BCFF)
        )
        // 2. La alerta de stock ahora está arriba y es clickeable.
        ProductItem(
            product = lowStockAlert,
            onClick = onLowStockClick
        )


        Spacer(modifier = Modifier.height(24.dp))


        Text(
            text = "ANALISIS DE VENTAS DIARIAS",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold ,
            color = Color(0xFFD0BCFF)
        )

        // 3. La tarjeta de análisis ahora está abajo y es solo informativa.
        SalesAnalyticsCard(percentage = 0.82f)
    }
}

// =================================================================================
// --- SECCIÓN 4: Componentes Personalizados de la Pantalla ---
// =================================================================================
@Composable
private fun SalesAnalyticsCard(percentage: Float) { // Ya no recibe 'onClick'
    val animatedProgress by animateFloatAsState(
        targetValue = percentage,
        animationSpec = tween(durationMillis = 1500, delayMillis = 300),
        label = "salesAnimation"
    )
    Card(
        modifier = Modifier.fillMaxWidth(), // Ya no tiene el .clickable
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Análisis de Ventas",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "${(percentage * 100).toInt()}% DE LA META",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f)
                )
                val liquidColors = listOf(Color(0xFF00C853), Color(0xFFA5D6A7))
                CylinderProgressBar(
                    modifier = Modifier.size(width = 50.dp, height = 90.dp),
                    progress = animatedProgress,
                    fillColors = liquidColors
                )
            }
        }
    }
}

@Composable
fun CylinderProgressBar(modifier: Modifier = Modifier, progress: Float, fillColors: List<Color>, containerColor: Color = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f), borderColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)) {
    Canvas(modifier=modifier){val strokeWidth=2.dp.toPx();val ellipseHeight=16.dp.toPx();val bodyHeight=size.height-ellipseHeight;drawRect(color=containerColor,topLeft=Offset(x=0f,y=ellipseHeight/2),size=Size(width=size.width,height=bodyHeight));drawOval(color=containerColor,topLeft=Offset(x=0f,y=size.height-ellipseHeight),size=Size(width=size.width,height=ellipseHeight));val numLines=3;for(i in 1..numLines){val y=(bodyHeight/(numLines+1))*i+(ellipseHeight/2);drawLine(color=borderColor.copy(alpha=0.5f),start=Offset(x=strokeWidth,y=y),end=Offset(x=size.width-strokeWidth,y=y),strokeWidth=1.dp.toPx())};drawLine(color=borderColor,start=Offset(x=strokeWidth/2,y=ellipseHeight/2),end=Offset(x=strokeWidth/2,y=bodyHeight+ellipseHeight/2),strokeWidth=strokeWidth);drawLine(color=borderColor,start=Offset(x=size.width-strokeWidth/2,y=ellipseHeight/2),end=Offset(x=size.width-strokeWidth/2,y=bodyHeight+ellipseHeight/2),strokeWidth=strokeWidth);drawOval(color=borderColor,topLeft=Offset(x=0f,y=0f),size=Size(width=size.width,height=ellipseHeight),style=Stroke(width=strokeWidth));drawOval(color=borderColor,topLeft=Offset(x=0f,y=size.height-ellipseHeight),size=Size(width=size.width,height=ellipseHeight),style=Stroke(width=strokeWidth));val fillHeight=(size.height-ellipseHeight)*progress.coerceIn(0f,1f);val topOffset=(size.height-ellipseHeight)*(1f-progress.coerceIn(0f,1f))+(ellipseHeight/2);if(fillHeight>0){val inset=strokeWidth;val transparentFillColors=fillColors.map{it.copy(alpha=0.7f)};val gradientBrush=Brush.verticalGradient(colors=transparentFillColors,startY=topOffset,endY=size.height);drawRect(brush=gradientBrush,topLeft=Offset(x=inset,y=topOffset),size=Size(width=size.width-(inset*2),height=fillHeight));drawOval(brush=gradientBrush,topLeft=Offset(x=inset,y=size.height-ellipseHeight),size=Size(width=size.width-(inset*2),height=ellipseHeight));drawOval(brush=gradientBrush,topLeft=Offset(x=inset,y=topOffset-(ellipseHeight/2)),size=Size(width=size.width-(inset*2),height=ellipseHeight))}}
}

@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit, // Recibe la acción de clic
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }, // Hace que toda la tarjeta sea clickeable
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier.size(80.dp).padding(8.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = product.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(text = product.price, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
            IconButton(onClick = { /* La acción principal está en la tarjeta */ }) {
                Icon(imageVector = Icons.Default.AddShoppingCart, contentDescription = "Añadir al carrito")
            }
        }
    }
}

// =================================================================================
// --- SECCIÓN 5: Modelo de Datos y Previsualización ---
// =================================================================================
data class Product(val name: String, val price: String, val imageRes: Int)

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun HomeScreenPreview() {
    GestorVentasTheme {
        HomeContent(
            innerPadding = PaddingValues(0.dp),
            onLowStockClick = {} // La preview funciona con una acción vacía
        )
    }
}