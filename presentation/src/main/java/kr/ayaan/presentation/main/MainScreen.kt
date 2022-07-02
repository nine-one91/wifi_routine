package kr.ayaan.presentation.main

import android.annotation.SuppressLint
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import kr.ayaan.presentation.R
import kr.ayaan.presentation.common.CustomButton
import kr.ayaan.presentation.common.CustomDialog


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    val bottomState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    if (bottomState.isVisible) {
        mainViewModel.getCurrentInfo()
    }

    ModalBottomSheetLayout(
        sheetBackgroundColor = Color.Black,
        sheetState = bottomState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = { ModalBottomSheet() }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Unit },
                    navigationIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                Icons.Filled.Menu,
                                contentDescription = "Menu",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                Icons.Outlined.Search,
                                contentDescription = "Search",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                Icons.Outlined.Notifications,
                                contentDescription = "Alarm",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        if (!bottomState.isVisible)
                            scope.launch {
                                bottomState.show()
                            }
                    },
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    shape = Shapes.Full

                ) {
                    Icon(Icons.Outlined.Add, contentDescription = "ADD")
                }
            }
        ) {
            if (mainViewModel.showWifiList.value) {
                WifiListDialog()
            }
            Column(
                modifier = Modifier
                    .padding(
                        it
                    )
                    .padding(
                        horizontal = 16.dp
                    )
            ) {
                Text(
                    "What`s up!",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                ListTile("Groups")
                WhiteBox(
                    content = {
                        Column(
                            Modifier
                                .padding(vertical = 31.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text("No Item Found.", style = MaterialTheme.typography.bodyLarge)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Add Todo Groups",
                                style = MaterialTheme.typography.bodyLarge,
                                textDecoration = TextDecoration.Underline
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                ListTile("Todos")
                WhiteBox(
                    content = {
                        Column(
                            Modifier
                                .padding(vertical = 20.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("No Item Found", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                )
            }
        }
    }

}

@Composable
fun ListTile(
    title: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                title,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.outline
                )
            )
        }

    }
}

@Composable
fun WhiteBox(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(Color.White)
    ) {
        content()
    }

}

@Composable
fun ModalBottomSheet(
    mainViewModel: MainViewModel = viewModel()
) {
    var todo by remember { mutableStateOf("") }

    val currentInfo: Map<String, String> by mainViewModel.currentWifiInfo.observeAsState(mapOf())

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .fillMaxHeight(fraction = 0.5F)
            .padding(16.dp)
    ) {
        Column {
            // 등록된 네트워크
            WhiteBox {
                Row(
                    Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 15.dp,
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Network",
                            style = MaterialTheme.typography.labelMedium.copy(MaterialTheme.colorScheme.outline)
                        )
                        Spacer(Modifier.height(8.dp))

                        Text(
                            currentInfo.getOrElse("ssid") { "no Wifi" },
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    IconButton(onClick = { mainViewModel.changeShowWifiList() }) {
                        Icon(Icons.Default.Menu, contentDescription = "Network List")
                    }

                }
            }
            Spacer(Modifier.height(8.dp))
            // Todos 추가
            WhiteBox(
                modifier = Modifier
                    .weight(1f)
            ) {
                TextField(
                    value = todo,
                    onValueChange = { todo = it },
                    modifier = Modifier.fillMaxSize(),
                    placeholder = { Text("Write Todo...") },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent

                    )

                )
            }
            Spacer(Modifier.height(8.dp))

            CustomButton(onClick = {}) {
                Text("Add")
            }
        }
    }
}

@Composable
fun WifiListDialog(
    mainViewModel: MainViewModel = viewModel()
) {
    val wifiList: List<ScanResult>? by mainViewModel.wifiList.observeAsState(null)
    Log.d("MAINSCREEN", "${wifiList}")
    CustomDialog(onDismissRequest = { mainViewModel.changeShowWifiList() }) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxHeight(0.7f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("WIFI LIST", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))
            LazyColumn {
                itemsIndexed(
                    wifiList ?: emptyList()
                ) { _, item ->
                    WifiCard(item) { mainViewModel.selectWifi(it) }
                }
            }
        }
    }
}

@Composable
fun WifiCard(data: ScanResult, onClick: (ScanResult) -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(35.dp)
            .clickable {
                onClick(data)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            when {
                WifiManager.calculateSignalLevel(data.level, 5) == 1 -> Icon(painter = painterResource(id = R.drawable.ic_wifi_1_bar), contentDescription = "wifi1")
                WifiManager.calculateSignalLevel(data.level, 5) == 2 -> Icon(painter = painterResource(id = R.drawable.ic_wifi_2_bar), contentDescription = "wifi2")
                WifiManager.calculateSignalLevel(data.level, 5) == 3 -> Icon(painter = painterResource(id = R.drawable.ic_wifi_3_bar), contentDescription = "wifi3")
                WifiManager.calculateSignalLevel(data.level, 5) == 4 -> Icon(painter = painterResource(id = R.drawable.ic_wifi_4_bar), contentDescription = "wifi4")
                WifiManager.calculateSignalLevel(data.level, 5) == 5 -> Icon(painter = painterResource(id = R.drawable.ic_wifi_5_bar), contentDescription = "wifi5")
            }

            Text(
                data.SSID,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

    }
}

