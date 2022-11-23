package mjms3.github.io.wheretoclimb

import org.junit.Assert
import org.junit.Test

import java.time.LocalDateTime


class CragSearchResultTest {
    @Test(expected = IllegalArgumentException::class)
    fun init_whenPercentageMatchIsBelowZero_throws() {
        CragSearchResult(
            percentageMatch = -1,
            cragName = "",
            resultGenerationTime = LocalDateTime.of(2022, 10, 1, 10, 0)
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun init_whenPercentageMatchIsAbove100_throws() {
        CragSearchResult(
            percentageMatch = 101,
            cragName = "",
            resultGenerationTime = LocalDateTime.of(2022, 10, 1, 10, 0)
        )
    }

    @Test
    fun init_whenPercentageMatchIsValid_percentageMatchSet() {
        val result = CragSearchResult(
            percentageMatch = 80,
            cragName = "",
            resultGenerationTime = LocalDateTime.of(2022, 10, 1, 10, 0)
        )
        Assert.assertEquals(80, result.percentageMatch)
    }
}