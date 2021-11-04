package groceries;

import org.junit.Test;

/**
 * Run the test for the solver
 * @author Jorge Ortiz
 */
public class TestShoppingSolver {
    @Test
    public void solverNotOptimized() {
        TestTools.testSolver(new ShoppingSolver(false));
    }

    @Test
    public void solverOptimized() {
        TestTools.testSolver(new ShoppingSolver(true));
    }
}
