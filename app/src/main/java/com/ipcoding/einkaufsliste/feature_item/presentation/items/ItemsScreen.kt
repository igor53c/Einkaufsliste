package com.ipcoding.einkaufsliste.feature_item.presentation.items

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ipcoding.einkaufsliste.feature_item.domain.util.ItemOrder
import com.ipcoding.einkaufsliste.feature_item.presentation.items.components.OneItem
import com.ipcoding.einkaufsliste.feature_item.presentation.util.Screen
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemsScreen(
    navController: NavController,
    viewModel: ItemsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(
                            ItemsEvent.Order(ItemOrder.Title(state.itemOrder.orderType))
                        )
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.SortByAlpha,
                        contentDescription = "Sort by alpha",
                        modifier = Modifier
                            .size(50.dp)
                    )
                }
                IconButton(
                    onClick = {
                        viewModel.onEvent(
                            ItemsEvent.Order(ItemOrder.Color(state.itemOrder.orderType))
                        )
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Sort",
                        modifier = Modifier
                            .size(50.dp)
                    )
                }
                IconButton(
                    onClick = {
                        navController.navigate(Screen.AddEditItemScreen.route)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        modifier = Modifier
                            .size(50.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.items) { item ->
                    OneItem(
                        item = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .combinedClickable (
                                onLongClick = {
                                    navController.navigate(
                                        Screen.AddEditItemScreen.route +
                                                "?itemId=${item.id}&itemColor=${item.color}"
                                    )
                                },
                                onClick = {
                                    viewModel.onEvent(ItemsEvent.UpdateItem(item))
                                }
                            )
                            ,
                        onDeleteClick = {
                            viewModel.onEvent(ItemsEvent.DeleteItem(item))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Item deleted",
                                    actionLabel = "Undo"
                                )
                                if(result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(ItemsEvent.RestoreItem)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}