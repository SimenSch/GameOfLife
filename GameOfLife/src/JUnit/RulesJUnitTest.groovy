package JUnit

/**
 * Created by Snorre on 03.05.2017.
 */
class RulesJUnitTest{
    void setUp() {
        super.setUp()

    }

    void tearDown() {

    }

    void testNextGeneration() {
        int x = 100
        int y = 80
        byte[][] grid = new byte [10][10]
        byte[][] nextGrid = new byte[x][y]
        for (int i = 1; i < x; i++) {
            for (int j = 1; j < y; j++) {
                if (rules(grid[i][j], countNeightbours(i, j), ruleSet) == 1) {

                    nextGrid[i][j] = 1
                } else;
            }
        }
        grid = nextGrid
    }

    void testCountNeightbours() {

    }
}
