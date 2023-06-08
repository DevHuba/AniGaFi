package eu.devhuba.anigafi.view.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import eu.devhuba.anigafi.R
import eu.devhuba.anigafi.ui.theme.DarkBlack
import eu.devhuba.anigafi.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    mvm: MainViewModel,
    navigate: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlack),
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo),
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            contentDescription = null
        )

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {


                mvm.login()


            }) {
            Text(text = "Login for Anime data")
        }

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                navigate()
            }) {
            Text(text = "Go Back")
        }
    }

}
