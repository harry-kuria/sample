package com.example.gettingstartedwithjetpackcompose.ui.theme.accountsDashboard

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AccountDetailsScreen(uuid: String, viewModel: DashboardViewModel, navController: NavController) {
//    val details = viewModel.accountDetails.collectAsState()
//    val isLoading = viewModel.detailsLoading.collectAsState(initial = true)
//    val error = viewModel.detailsError.collectAsState()
//
//    val account = details.value
//
//    LaunchedEffect(uuid) {
//        viewModel.loadAccountDetails(uuid = uuid)
//    }
//
//    Scaffold(
//        topBar = { TopAppBar(title = {Text("Account details")},
//            navigationIcon = { IconButton(onClick = { navController.popBackStack() })  {
//                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Back") }} )
//        }
//    ) { padding ->
//        when {
//            isLoading.value -> {
//                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
//                    CircularProgressIndicator()
//                }
//            }
//            error.value != null -> {
//                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
//                    Text(text = "Error: ${error.value}")
//                }
//            }
//            account != null -> {
//                LazyColumn(modifier = Modifier.padding(16.dp).fillMaxSize()) {
//                    item {
//                        Text("Name: ${account.names}", style = MaterialTheme.typography.titleMedium)
//                        Text("Gender: ${account.gender}")
//                        Text("Phone: ${account.phone}")
//                        Spacer(Modifier.height(16.dp))
//                    }
//
//                    item {
//                        Text("Wallets", style = MaterialTheme.typography.titleMedium)
//                        account.wallets.forEach {
//                            Text("- ${it.name} (${it.currency}) -> ${it.balance}, Default: ${it.isDefault == 1}")
//                        }
//                        Spacer(Modifier.height(16.dp))
//                    }
//
//                    item {
//                        Text("Dependants", style = MaterialTheme.typography.titleMedium)
//                        account.dependants.forEach {
//                            Text("- ${it.name}, Age: ${it.age}, Active: ${it.status}")
//                        }
//                        Spacer(Modifier.height(16.dp))
//                    }
//
//                    item {
//                        Text("IOTs", style = MaterialTheme.typography.titleMedium)
//                        account.iots.forEach { iot ->
//                            Text("- Serial: ${iot.serialNumber}, Status: ${iot.status}," +
//                                    " Assignee: ${iot.assignee.name}")
//                        }
//                    }
//                }
//            }
//            else -> {
//                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
//                    Text("No data available.")
//                }
//            }
//        }
//
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDetailsScreen(
    uuid: String,
    viewModel: DashboardViewModel,
    navController: NavController
) {
    // Trigger loading once when screen opens
    LaunchedEffect(uuid) {
        Log.d("DETAILS", "Calling loadAccountDetails for uuid = $uuid")
        viewModel.loadAccountDetails(uuid)

    }

    val account = viewModel.accountDetails.collectAsState()
    val isLoading = viewModel.detailsLoading.collectAsState()
    val error = viewModel.detailsError.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Account details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->

        when {
            account.value != null -> {
                val acc = account.value!!
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    item {
                        Text("Name: ${acc.names}", style = MaterialTheme.typography.titleMedium)
                        Text("Gender: ${acc.gender}")
                        Text("Phone: ${acc.phone}")
                        Text("KYC Status: ${acc.kycStatus}")
                        Text("Account created on: ${acc.createdAt}")
                        Text("Account updated on: ${acc.updatedAt}")
                        Text("Notes: ${acc.notes}")
                        Spacer(Modifier.height(16.dp))
                    }

//                    if (!acc.wallets.isNullOrEmpty()) {
//                        item {
//                            Text("Wallets", style = MaterialTheme.typography.titleMedium)
//                            acc.wallets.forEach { wallet ->
//                                Column(modifier = Modifier.padding(vertical = 8.dp)) {
//                                    Text(
//                                        "- ${wallet.name} (${wallet.currency}) : Balance: ${wallet.balance}"
//                                    )
//                                    Text("Default: ${wallet.isDefault == 1}")
//
////                                    if (!wallet.iots.isNullOrEmpty()) {
////                                        Text("IOTs:")
////                                        wallet.iots.forEach { iot ->
////                                            Text("- Serial number: ${iot.serialNumber}, Status: ${iot.status}," +
////                                                    " Assignee: ${iot.assignee?.name}")
////                                        }
////                                    }
//                                }
//                                Spacer(Modifier.height(16.dp))
//                            }
//                        }
//                    }

                    if (!acc.dependants.isNullOrEmpty()) {
                        item {
                            Text("Dependants", style = MaterialTheme.typography.titleMedium)
                            acc.dependants.forEach { dependant ->
                                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                    Text("- ${dependant.names}, Age: ${dependant.age}")
                                    if (dependant.iots.isNotEmpty()) {
                                        Text("IOTs:")
                                        dependant.iots.forEach { iot ->
                                            Text("- Serial number: ${iot.serialNumber}, Status: ${iot.status}," +
                                                    " Assignee: ${iot.assignee?.name}")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            isLoading.value -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            error.value != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Error: ${error.value}")
                }
            }

            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No account data available.")
                }
            }
        }
    }
}
