package eu.devhuba.anigafi

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.devhuba.anigafi.ui.theme.AniGaFiTheme

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AniGaFiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    MyApp(navController = navController)
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MyApp(navController: NavController) {

    val swappableState = rememberSwipeableState(0)

    Scaffold(
        topBar = { },
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .height(25.dp).border( 4.dp, color = Color.Red ),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "this is bottom bar", 
                    fontSize = 30.sp)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .swipeable(
                    state = swappableState,
                    anchors = mapOf(
                        0f to 0,
                        1f to 1
                    ),
                    thresholds = { _, _ -> FractionalThreshold(0.5f) },
                    orientation = Orientation.Horizontal,
                    reverseDirection = true,
                )
                .background(Color.White)
        ) {
            val currentPage = swappableState.currentValue
            val targetPage = swappableState.targetValue

            when (currentPage) {
                0 -> {
                    Screen1()
                }
                1 -> {
                    Screen2()
                }
                2 -> {
                    Screen3()
                }
            }

            when (targetPage) {
                0 -> {
                    SwipeToScreen1()
                }
                1 -> {
                    SwipeToScreen2()
                }
                2 -> {
                    SwipeToScreen3()
                }
            }
        }
    }
}

@Composable
fun Screen1() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Screen 1", fontSize = 24.sp)
    }
}

@Composable
fun Screen2() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Screen 2", fontSize = 24.sp)
    }
}

@Composable
fun Screen3() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Screen 3", fontSize = 24.sp)
    }
}

@Composable
fun SwipeToScreen1() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Swipe right to go to Screen 1", color = Color.White, fontSize = 24.sp)
    }
}

@Composable
fun SwipeToScreen2() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Swipe left to go to Screen 2", color = Color.White, fontSize = 24.sp)
    }
}

@Composable
fun SwipeToScreen3() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Swipe left to go to Screen 3", color = Color.White, fontSize = 24.sp)
    }
}