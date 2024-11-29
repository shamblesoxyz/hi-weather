package com.example.hiweather.screens

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hiweather.navigation.WeatherScreens
import com.example.hiweather.components.TopBar
import com.example.hiweather.viewmodels.SettingScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun SettingScreen(navController: NavController, settingScreenViewModel: SettingScreenViewModel) {
    LaunchedEffect(key1 = Unit) {
        settingScreenViewModel.getFromSharedPreferences("temp_unit")
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    text = "Setting"
                ) {
                    navController.popBackStack()
                }
            }
        ) {
            SettingsContent(
                paddingValues = it,
                navController = navController,
                settingScreenViewModel = settingScreenViewModel
            )
        }
    }
}

@Composable
fun SettingsContent(
    paddingValues: PaddingValues,
    navController: NavController,
    settingScreenViewModel: SettingScreenViewModel
) {
    val nestedScrollConnection = object : NestedScrollConnection {}
    val verticalScrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val savedValue = settingScreenViewModel.temperatureUnit.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
            .nestedScroll(nestedScrollConnection)
            .verticalScroll(verticalScrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 15.dp, end = 15.dp),
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation(0.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SettingRow(
                    dropdownItems = arrayListOf("Celsius (°C)", "Kelvin (°k)", "Fahrenheit (°F)"),
                    selectedDropdownMenuItem = savedValue.value,
                    onItemClick = {
                        scope.launch {
                            settingScreenViewModel.saveToSharedPreferences("temp_unit", it)
                            settingScreenViewModel.getFromSharedPreferences("temp_unit")
                        }
                    }
                ) {
                    Text(
                        text = "Temperature Unit",
                        style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                    )
                    Text(
                        text = savedValue.value,
                        style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Normal)
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.Gray
                )
                SettingRow(
                    navigate = {
                        navController.navigate(WeatherScreens.AboutScreen.name)
                    }
                ) {
                    Text(
                        text = "About",
                        style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.Gray
                )
                SettingRow(
                    navigate = {
                        navController.navigate(WeatherScreens.BlogScreen.name)
                    }
                ) {
                    Text(
                        text = "Blog",
                        style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                    )
                }
            }
        }
    }
}

@Composable
fun SettingRow(
    dropdownItems: ArrayList<String> = arrayListOf(),
    selectedDropdownMenuItem: String = "Celsius (°C)",
    onItemClick: (String) -> Unit = {},
    navigate: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val isContextMenuVisible = rememberSaveable {
        mutableStateOf(false)
    }
    val pressOffset = remember {
        mutableStateOf(DpOffset.Zero)
    }
    val itemHeight = remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .indication(interactionSource, LocalIndication.current)
            .onSizeChanged {
                itemHeight.value = with(density) { it.height.toDp() }
            }
            .pointerInput(true) {
                detectTapGestures(
                    onPress = {
                        if (dropdownItems.isNotEmpty()) {
                            val press = PressInteraction.Press(it)
                            interactionSource.emit(press)
                            tryAwaitRelease()
                            interactionSource.emit(PressInteraction.Release(press))
                            isContextMenuVisible.value = true
                            pressOffset.value = DpOffset(it.x.toDp(), it.y.toDp())
                        } else {
                            navigate()
                        }
                    }
                )
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 50.dp)
                .wrapContentHeight()
                .padding(start = 20.dp, end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            content()
        }
        MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(16.dp))) {
            DropdownMenu(
                expanded = isContextMenuVisible.value,
                onDismissRequest = { isContextMenuVisible.value = false },
                offset = pressOffset.value.copy(y = pressOffset.value.y - itemHeight.value)
            ) {
                dropdownItems.forEach {
                    DropdownMenuItem(
                        onClick = {
                            onItemClick(it)
                            isContextMenuVisible.value = false
                        },
                        text = {
                            Text(
                                text = it,
                                style = TextStyle(
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = if ((selectedDropdownMenuItem == it) || (selectedDropdownMenuItem.isEmpty() && it=="Celsius (°C)")) MaterialTheme.colorScheme.primary else Color.Gray
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}