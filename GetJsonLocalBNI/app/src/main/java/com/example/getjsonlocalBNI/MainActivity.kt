package com.example.getjsonlocalBNI

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.getjsonlocalBNI.data.util.JsonUtil
import com.example.getjsonlocalBNI.data.util.PromoItem
import com.example.getjsonlocalBNI.ui.theme.RecyclerViewJCYTTTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecyclerViewJCYTTTheme {
                val jsonString = JsonUtil.readJsonFromAssets("promo.json", LocalContext.current)
                val promoItems = JsonUtil.fromJson(jsonString)
                RecyclerView(promoItems)
            }
        }
    }
}

@Composable
fun ListItem(promoItem: PromoItem){

    val expanded = remember { mutableStateOf(false)}
    val extraPadding by animateDpAsState(
        if (expanded.value) 24.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(color = MaterialTheme.colors.secondary,
    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)){

        Column(modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()) {

            Row{

                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(text = "Promo BNI",color = MaterialTheme.colors.primaryVariant)

                }

                OutlinedButton(onClick = { expanded.value = !expanded.value } ) {
                    Text(if (expanded.value) "Show less" else "Show more", color = MaterialTheme.colors.primaryVariant)
                }
            }

            if (expanded.value){

                Column(modifier = Modifier.padding(
                    bottom = extraPadding.coerceAtLeast(0.dp)
                )) {
                    Text(text = promoItem.desc, color = MaterialTheme.colors.primaryVariant)
                }

            }
            }

        }



    }



@Composable
fun RecyclerView(promoItems: List<PromoItem> = emptyList()) {

    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {

        items(items = promoItems) { promoItem ->
            ListItem(promoItem)
        }

    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RecyclerViewJCYTTTheme {
        RecyclerView()
    }
}