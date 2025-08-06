package com.example.gettingstartedwithjetpackcompose.features.allAccounts.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.gettingstartedwithjetpackcompose.R
import com.example.gettingstartedwithjetpackcompose.data.network.accountsNetwork.AccountsDto
import com.example.gettingstartedwithjetpackcompose.features.allAccounts.DashboardViewModel
import com.example.gettingstartedwithjetpackcompose.features.navigation.Routes


//Card version
@Composable
fun SearchBar( query: String, onQueryChange: (String) -> Unit, onSearch: () -> Unit) {
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(Color.LightGray, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = { focusManager.clearFocus()
                        onSearch()
                    }
                ),
                decorationBox = { innerTextField ->
                    if (query.isEmpty()) {
                        Text(
                            text = "Search by name or phone number",
                            style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                        )
                    }
                    innerTextField()
                }
            )

            IconButton( onClick = { focusManager.clearFocus()
                    onSearch() },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Gray
                )
            }
        }
    }
}


@Composable
fun ExpandableUserCard(account: AccountsDto, navController : NavController) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            //.background(Color.LightGray)
            .clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xEEF9F9F9)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    //Text(text = account.id, style = MaterialTheme.typography.headlineMedium)
                    Text(
                        text = account.names,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                    Text(
                        text = account.phone,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                }

                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowDown else Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "dropdown", tint = Color.DarkGray
                    )
                }
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Gender: ${account.gender}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                    Text(
                        "KYC: ${account.kycStatus}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                    Text(
                        "UUID: ${account.uuid}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                    Text(
                        "Code: ${account.code}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                    Text(
                        "Notes: ${account.notes ?: "None"}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                    Text(
                        "Created: ${account.createdAt}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                    Text(
                        "Updated: ${account.updatedAt}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(5.dp))
                    TextButton(onClick = { navController.navigate("${Routes.ACCOUNT_DETAILS}/${account.uuid}") }) {
                        Text(text = "See more...", color = Color.Blue)
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun AccountsDashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val accounts by viewModel.accounts.collectAsState()
    val error by viewModel.error.collectAsState()
    val loading by viewModel.isLoading.collectAsState()
    val focusManager = LocalFocusManager.current

    val query by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All Accounts") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFDE91EA),
                    titleContentColor = Color.Black,
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.HOME) }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_notes),
                            contentDescription = "Notes dashboard",
                            tint = Color.Black,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    IconButton(onClick = { navController.navigate(Routes.MY_ACCOUNT) }) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "My Account",
                            tint = Color.Black,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            )
        },
    ) { paddingValues ->

        Column(
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(horizontal = 3.dp)
        ) {

            Spacer(modifier = Modifier.height(4.dp))
            SearchBar(
                query = query,
                onQueryChange = { viewModel.updateSearchQuery(it) },
                onSearch = {
                    focusManager.clearFocus()
                    viewModel.loadAccounts(query)
                }
            )

            val pullRefreshState = rememberPullRefreshState(
                refreshing = loading,
                onRefresh = { viewModel.loadAccounts() }
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
            )  {
                when {
                    error != null -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row {
                                Text(text = error ?: "", color = Color.Red)
                                TextButton(onClick = { viewModel.loadAccounts() }) { Text("Retry") }
                            }
                        }
                    }

                    accounts.isEmpty() && !loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "No accounts found")
                        }
                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            items(accounts) { account ->
                                ExpandableUserCard(account, navController = navController)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
                PullRefreshIndicator(
                    refreshing = loading,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )

            }
        }
    }
}



//TABLE VERSION
//data class UserInfo(
//    val names: String,
//    val phone: String,
//    val uuid: String,
//    val gender: String,
//    val code: String,
//    val notes: String?,
//    val kycStatus: String,
//    val createdAt: String,
//    val updatedAt: String
//)
//
//
//DATA TABLE
//@Composable
//fun UserTableHeader() {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//            .background(Color.LightGray),
//        horizontalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        Text("Name", modifier = Modifier.weight(2f), fontWeight = FontWeight.Bold)
//        Text("Phone", modifier = Modifier.weight(2f), fontWeight = FontWeight.Bold)
////        Text("Gender", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
////        Text("KYC", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
////        Text("Date created", modifier = Modifier.weight(2f), fontWeight = FontWeight.Bold)
//    }
//}
//
//@Composable
//fun UserRow(userInfo: UserInfo) {
//    var expanded by remember { mutableStateOf(false) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { expanded = !expanded }
//            .padding(vertical = 8.dp, horizontal = 16.dp)
//    ) {
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//            Text(userInfo.names, modifier = Modifier.weight(2f))
//            Text(userInfo.phone, modifier = Modifier.weight(2f))
////            Text(userInfo.gender, modifier = Modifier.weight(1f))
////            Text(userInfo.kycStatus, modifier = Modifier.weight(1f))
////            Text(userInfo.createdAt, modifier = Modifier.weight(2f))
//        }
//
//        if (expanded) {
//            Spacer(modifier = Modifier.height(4.dp))
//            Column(modifier = Modifier.padding(start = 16.dp)) {
//                Text("Gender: ${userInfo.gender}", style = MaterialTheme.typography.bodyMedium)
//                Text("KYC Status: ${userInfo.kycStatus}", style = MaterialTheme.typography.bodyMedium)
//                Text("UUID: ${userInfo.uuid}", style = MaterialTheme.typography.bodyMedium)
//                Text("Code: ${userInfo.code}", style = MaterialTheme.typography.bodyMedium)
//                Text("Notes: ${userInfo.notes}", style = MaterialTheme.typography.bodyMedium)
//                Text("Date created: ${userInfo.createdAt}", style = MaterialTheme.typography.bodyMedium)
//                Text("Date updated: ${userInfo.updatedAt}", style = MaterialTheme.typography.bodyMedium)
//            }
//        }
//    }
//}
//
//@Composable
//fun UserTable(users: List<UserInfo>) {
//    Column(modifier = Modifier.fillMaxWidth()) {
//        UserTableHeader()
//        Divider()
//        users.forEach { user ->
//            UserRow(user)
//            Divider()
//        }
//    }
//}
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AccountsDashboardScreen(
//    viewModel: DashboardViewModel = hiltViewModel(),
//    navController: NavHostController
//) {
//    val accounts by viewModel.accounts.collectAsState()
//    val error by viewModel.error.collectAsState()
//    val loading by viewModel.isLoading.collectAsState()
//
//    val users = accounts.map { account ->
//        UserInfo(
//            names = account.names,
//            phone = account.phone,
//            uuid = account.uuid,
//            gender = account.gender,
//            code = account.code,
//            notes = account.notes,
//            kycStatus = account.kycStatus,
//            createdAt = account.createdAt,
//            updatedAt = account.updatedAt
//        )
//    }
//
//    val refreshState = rememberSwipeRefreshState(isRefreshing = loading)
//
//    Scaffold( modifier = Modifier
//        .fillMaxSize()
//        .background(Color.White),
//        topBar = {
//            TopAppBar(
//                title = { Text("All Accounts") },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color(0xFFDE91EA),
//                    titleContentColor = Color.Black,
//                ),
//                actions = {
//                    IconButton(onClick = { navController.navigate(Routes.MY_ACCOUNT) }) {
//                        Icon(
//                            imageVector = Icons.Default.AccountCircle,
//                            contentDescription = "My Account",
//                            tint = Color.Black,
//                            modifier = Modifier.size(40.dp)
//                        )
//                    }
//                    IconButton(onClick = { navController.navigate(Routes.HOME) }) {
//                        Icon(
//                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_notes),
//                            contentDescription = "Notes dashboard",
//                            tint = Color.Black,
//                            modifier = Modifier.size(40.dp)
//                        )
//                    }
//                }
//            )
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White)
//                .padding(paddingValues)
//        ) {
//            Text(
//                text = "All Users",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                textAlign = TextAlign.Center,
//                style = MaterialTheme.typography.headlineMedium,
//                color = Color.Black
//            )
//
//            SwipeRefresh(
//                state = refreshState,
//                onRefresh = { viewModel.loadAccounts() }
//            ) {
//                when {
//                    error != null -> {
//                        Column(modifier = Modifier
//                            .fillMaxSize()
//                            .padding(16.dp)) {
//                            Text(text = error ?: "", color = Color.Red)
//                            TextButton(onClick = { viewModel.loadAccounts() }) {
//                                Text("Retry")
//                            }
//                        }
//                    }
//
//                    users.isEmpty() && !loading -> {
//                        Box(
//                            modifier = Modifier.fillMaxSize(),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(text = "No accounts found")
//                        }
//                    }
//
//                    else -> {
//                        LazyColumn {
//                            item {
//                                UserTable(users = users)
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
