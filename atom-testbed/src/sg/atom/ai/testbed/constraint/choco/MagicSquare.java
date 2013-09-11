package sg.atom.testbed.constraint.choco;

import org.kohsuke.args4j.Option;
import org.slf4j.LoggerFactory;
import solver.Solver;
import solver.constraints.IntConstraintFactory;
import solver.search.strategy.selectors.variables.ImpactBased;
import solver.variables.IntVar;
import solver.variables.VariableFactory;
import util.tools.StringUtils;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * CSPLib prob019:<br/>
 * "An order n magic square is a n by n matrix containing the numbers 1 to n^2, with each row,
 * column and main diagonal equal the same sum.
 * As well as finding magic squares, we are interested in the number of a given size that exist."
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 31/03/11
 */
public class MagicSquare extends AbstractProblem {

    @Option(name = "-n", usage = "Magic square size.", required = false)
    int n = 5;

    IntVar[] vars;

    @Override
    public void createSolver() {
        solver = new Solver("Magic square");
    }

    @Override
    public void buildModel() {
        int ms = n * (n * n + 1) / 2;

        IntVar[][] matrix = new IntVar[n][n];
        IntVar[][] invMatrix = new IntVar[n][n];
        vars = new IntVar[n * n];

        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++, k++) {
                matrix[i][j] = VariableFactory.enumerated("square" + i + "," + j, 1, n * n, solver);
                vars[k] = matrix[i][j];
                invMatrix[j][i] = matrix[i][j];
            }
        }

        IntVar[] diag1 = new IntVar[n];
        IntVar[] diag2 = new IntVar[n];
        for (int i = 0; i < n; i++) {
            diag1[i] = matrix[i][i];
            diag2[i] = matrix[(n - 1) - i][i];
        }

        solver.post(IntConstraintFactory.alldifferent(vars, "BC"));

        int[] coeffs = new int[n];
        Arrays.fill(coeffs, 1);
        IntVar msv = VariableFactory.fixed(ms, solver);
        for (int i = 0; i < n; i++) {
            solver.post(IntConstraintFactory.scalar(matrix[i], coeffs, msv));
            solver.post(IntConstraintFactory.scalar(invMatrix[i], coeffs, msv));
        }
        solver.post(IntConstraintFactory.scalar(diag1, coeffs, msv));
        solver.post(IntConstraintFactory.scalar(diag2, coeffs, msv));

        // Symetries breaking
        solver.post(IntConstraintFactory.arithm(matrix[0][n - 1], "<", matrix[n - 1][0]));
        solver.post(IntConstraintFactory.arithm(matrix[0][0], "<", matrix[n - 1][n - 1]));
        solver.post(IntConstraintFactory.arithm(matrix[0][0], "<", matrix[n - 1][0]));

    }

    @Override
    public void configureSearch() {
        solver.set(new ImpactBased(vars, 2, 3, 10, 29091981L, false));
    }

    @Override
    public void solve() {
        solver.findSolution();
    }

    @Override
    public void prettyOut() {
        StringBuilder st = new StringBuilder();
        String line = "+";
        for (int i = 0; i < n; i++) {
            line += "----+";
        }
        line += "\n";
        st.append(line);
        for (int i = 0; i < n; i++) {
            st.append("|");
            for (int j = 0; j < n; j++) {
                st.append(StringUtils.pad(vars[i * n + j].getValue() + "", -3, " ")).append(" |");
            }
            st.append(MessageFormat.format("\n{0}", line));
        }
        st.append("\n\n\n");
        LoggerFactory.getLogger("bench").info(st.toString());
    }

    public static void main(String[] args) {
        new MagicSquare().execute(args);
    }
}
