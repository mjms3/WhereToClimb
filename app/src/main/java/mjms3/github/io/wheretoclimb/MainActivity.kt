package mjms3.github.io.wheretoclimb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import mjms3.github.io.wheretoclimb.ui.theme.WhereToClimbTheme
import java.time.LocalDateTime


data class CragSearchResult(
    val cragName: String,
    val percentageMatch: Int,
    val resultGenerationTime: LocalDateTime
)
{
    init{
        require(this.percentageMatch in 0..100)
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhereToClimbTheme {
                val materialBlue700 = Color(0xFF1976D2)
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("TopAppBar") },
                            backgroundColor = materialBlue700
                        )
                    },
                    content = { GetSearchResultsContent() },
                    bottomBar = {
                        BottomAppBar(backgroundColor = materialBlue700) {
                            Text("BottomAppBar")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun GetSearchResultsContent() {
    var searchResults = getSearchResults()
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        searchResults.forEach() { result ->
            GetSearchResultCard(result = result)
        }
    }
}

@Composable
fun GetSearchResultCard(result: CragSearchResult) {
    val modifier = Modifier.padding(10.dp)
    Card(
        elevation = 10.dp,
        modifier = modifier.fillMaxWidth(),
    ) {
        Row() {
            Canvas(modifier = Modifier.size(50.dp)) {
                val height = size.height
                drawRect(
                    size = Size(width = 20f, height = height),
                    color = getSearchResultBarColor(result),
                )
            }
            Text(text = result.cragName, modifier = modifier)
        }
    }
}

fun getSearchResultBarColor(searchResult: CragSearchResult): Color {
    val red = kotlin.math.min(2.0f * (1 - searchResult.percentageMatch / 100), 1.0f)
    val green = kotlin.math.min(2.0f * searchResult.percentageMatch / 100, 1.0f)
    return Color(red = red, green = green, blue = 0.0f)
}

fun getSearchResults(): MutableList<CragSearchResult> {
    val searchResults = mutableListOf<CragSearchResult>(
        CragSearchResult(
            cragName = "Stanage",
            percentageMatch = 100,
            resultGenerationTime = LocalDateTime.of(2022, 10, 1, 10, 0)
        ),
        CragSearchResult(
            cragName = "Shining Clough",
            percentageMatch = 2,
            resultGenerationTime = LocalDateTime.of(2022, 10, 1, 10, 0)
        )
    )
    return searchResults
}