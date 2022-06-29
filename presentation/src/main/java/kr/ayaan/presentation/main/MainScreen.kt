package kr.ayaan.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import kr.ayaan.presentation.common.CustomButton

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    val bottomState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
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
fun ModalBottomSheet() {
    var todo by remember { mutableStateOf("") }

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
                        Text("My Network", style = MaterialTheme.typography.bodyMedium)
                    }
                    Icon(Icons.Default.Menu, contentDescription = "Network List")
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
