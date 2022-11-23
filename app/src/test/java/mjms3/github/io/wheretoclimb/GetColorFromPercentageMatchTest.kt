package mjms3.github.io.wheretoclimb

import androidx.compose.ui.graphics.Color
import org.junit.Assert
import org.junit.Test

class GetColorFromPercentageMatchTest {
    @Test
    fun when100PercentMatch_colorIsGreen() {
        Assert.assertEquals(
            Color.Green, getColorFromPercentageMatch(100)
        )
    }

    @Test
    fun when0PercentMatch_colorIsRed() {
        Assert.assertEquals(
            Color.Red, getColorFromPercentageMatch(0)
        )
    }

    @Test
    fun when50PercentMatch_colorIsYellow() {
        Assert.assertEquals(
            Color.Yellow, getColorFromPercentageMatch(50)
        )
    }
}