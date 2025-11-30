package com.kolaswu.stocknotification

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kolaswu.stocknotification.ui.theme.StockNotificationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StockNotificationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

/*登入頁面*/
@Composable
fun LoginScreen(){
    var account by remember { mutableStateOf("") } //確保UI畫面之質不會一直被重設
    var password by remember { mutableStateOf("") }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "股票價格通知系統",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(
                value = account,
                onValueChange = {account = it},
                label = {Text("帳號")},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = {password = it},
                label = {Text("密碼")},
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = {}) {
                Text("Login")
            }
        }
    }
}

@Composable
fun MainTabScaffold(){
    var currentTab by rememberSaveable {mutableStateOf(BottomTab.Home) }
    Scaffold(
        bottomBar = {
            NavigationBar{
                BottomTab.values().forEach {
                    tab -> NavigationRailItem(
                    selected = currentTab == tab,
                    onClick = {currentTab = tab},
                    icon = {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.title
                        )
                    },
                    label = {Text(tab.title)}
                )
                }
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize().padding()){
            when(currentTab){
                BottomTab.Home -> HomeScreen()
                BottomTab.Search -> StockSearchScreen()
                BottomTab.Favorite -> MyFavoriteScreen()
                BottomTab.Notification -> NotificationListScreen()
            }
        }
    }
}

/*
定義四個tab
 */
enum class BottomTab(
    val title: String,
    val icon:androidx.compose.ui.graphics.vector.ImageVector
){
    Home("首頁", Icons.Filled.Home),
    Search("股票查詢",Icons.Filled.Search),
    Favorite("我的最愛", Icons.Filled.Favorite),
    Notification("通知列表", Icons.Filled.List)
}

@Composable
fun HomeScreen(){
    CenterTitle(title = "首頁 － 今日股市最新消息")
}

@Composable
fun StockSearchScreen(){
    CenterTitle(title = "股票查詢 － 依名稱/代號查詢，可加入我的最愛")
}

@Composable
fun MyFavoriteScreen(){
    CenterTitle(title = "我的最愛 － 設定通知機制和觀看股票")
}

@Composable
fun NotificationListScreen(){
    CenterTitle(title = "通知列表 ex: XXX在2025.01.01 00:00達 ＄XX")
}

@Composable
fun CenterTitle(title: String){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    StockNotificationTheme {
        LoginScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun MainPagePreview() {
    StockNotificationTheme {
        MainTabScaffold()
    }
}