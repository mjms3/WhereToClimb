package mjms3.github.io.wheretoclimb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import mjms3.github.io.wheretoclimb.ui.theme.WhereToClimbTheme
import java.time.LocalDateTime

data class CragSearchResult(
    val cragName: String,
    val percentageMatch: Int,
    val resultGenerationTime: LocalDateTime
)

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
        searchResults.forEach(){
            result -> GetSearchResultCard(result = result)
        }
    }
}

@Composable
fun GetSearchResultCard(result: CragSearchResult) {
    Text(result.cragName, fontSize = 36.sp)
}

fun getSearchResults(): MutableList<CragSearchResult> {
    val searchResults = mutableListOf<CragSearchResult>(
        CragSearchResult(
            cragName = "Stanage",
            percentageMatch = 80,
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