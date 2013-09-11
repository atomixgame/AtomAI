package sg.atom.testbed.constraint.choco;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import solver.Solver;
import solver.explanations.ExplanationFactory;
import solver.propagation.hardcoded.PropagatorEngine;

/**
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 31/03/11
 */
public abstract class AbstractProblem {

    enum Level {
        SILENT(-10), QUIET(0), VERBOSE(10);

        int level;

        Level(int level) {
            this.level = level;
        }


        public int getLevel() {
            return level;
        }
    }

    @Option(name = "-l", aliases = "--log", usage = "Quiet resolution", required = false)
    Level level = Level.VERBOSE;

    @Option(name = "-s", aliases = "--seed", usage = "Seed for Shuffle propagation engine.", required = false)
    protected long seed = 29091981;

    @Option(name = "-e", aliases = "--exp-eng", usage = "Type of explanation engine to plug in")
    ExplanationFactory expeng = ExplanationFactory.NONE;

    @Option(name = "-fe", aliases = "--flatten-expl", usage = "Flatten explanations (automatically plug ExplanationFactory.SILENT in if undefined).", required = false)
    protected boolean fexp = false;

    protected Solver solver;

    private boolean userInterruption = true;

    public void printDescription() {
    }

    public Solver getSolver() {
        return solver;
    }

    public abstract void createSolver();

    public abstract void buildModel();

    public abstract void configureSearch();

    public abstract void solve();

    public abstract void prettyOut();

    public final boolean readArgs(String... args) {
        CmdLineParser parser = new CmdLineParser(this);
        parser.setUsageWidth(160);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java " + this.getClass() + " [options...]");
            parser.printUsage(System.err);
            System.err.println();
            return false;
        }
        return true;
    }

    protected void overrideExplanation() {
        if (!solver.getExplainer().isActive()) {
            if (expeng != ExplanationFactory.NONE) {
                expeng.plugin(solver, fexp);
            } else if (fexp) {
                ExplanationFactory.SILENT.plugin(solver, fexp);
            }
        }
    }

    private final boolean userInterruption() {
        return userInterruption;
    }

    public final void execute(String... args) {
        if (this.readArgs(args)) {
            final Logger log = LoggerFactory.getLogger("bench");
            this.printDescription();
            this.createSolver();
            this.buildModel();
            this.configureSearch();

            overrideExplanation();

            solver.set(new PropagatorEngine(solver));

            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    if (level.getLevel() > Level.QUIET.getLevel()) {
                        prettyOut();
                    }
                    if (level.getLevel() > Level.QUIET.getLevel()) {
                        log.info("{}", solver.getMeasures().toString());
                    } else if (level.getLevel() > Level.SILENT.getLevel()) {
                        log.info("[STATISTICS {}]", solver.getMeasures().toOneLineString());
                    }
                    if (userInterruption()) {
                        if (level.getLevel() > Level.SILENT.getLevel()) {
                            log.info("Unexpected resolution interruption!");
                        }
                    }

                }
            });

            this.solve();
            userInterruption = false;
        }
    }

}
