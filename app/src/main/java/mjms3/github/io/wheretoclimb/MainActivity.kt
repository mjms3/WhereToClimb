package mjms3.github.io.wheretoclimb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import mjms3.github.io.wheretoclimb.ui.theme.WhereToClimbTheme
import java.time.LocalDateTime
import kotlin.math.min


data class CragSearchResult(
    val cragName: String,
    val percentageMatch: Int,
    val resultGenerationTime: LocalDateTime
) {
    init {
        require(this.percentageMatch in 0..100) {
            "percentageMatch must be between 0 and 100. It was ${this.percentageMatch}."
        }
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
    val searchResults = getSearchResults()
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
    var multiplier by remember{ mutableStateOf(1f) }
    Card(
        elevation = 10.dp,
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Canvas(modifier = Modifier
                .height(50.dp)
                .width(5.dp)) {
                val height = size.height
                val width = size.width
                drawRect(
                    size = Size(width = width, height = height),
                    color = getSearchResultBarColor(result),
                )
            }
            Text(
                result.cragName,
                maxLines = 1, // modify to fit your need
                overflow = TextOverflow.Visible,
                style = LocalTextStyle.current.copy(
                    fontSize = LocalTextStyle.current.fontSize * multiplier
                ),
                onTextLayout = {
                    if (it.hasVisualOverflow) {
                        multiplier *= 0.9f
                    }
                },
                modifier = modifier,
            )
        }
    }
}

private fun getSearchResultBarColor(searchResult: CragSearchResult): Color {
    val percentageMatch = searchResult.percentageMatch
    return getColorFromPercentageMatch(percentageMatch)
}

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
fun getColorFromPercentageMatch(percentageMatch: Int): Color {
    val red = min(2.0f * (1 - percentageMatch / 100), 1.0f)
    val green = min(2.0f * percentageMatch / 100, 1.0f)
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
        ),
        CragSearchResult(
            cragName = "Another crag with a really long name, yes a really really really long name",
            percentageMatch = 50,
            resultGenerationTime = LocalDateTime.of(2022, 10, 1, 10, 0)
        ),
    )
    searchResults.sortByDescending { it.percentageMatch }
    return searchResults
}