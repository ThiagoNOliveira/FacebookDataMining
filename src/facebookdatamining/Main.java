package facebookdatamining;

import facebookdatamining.Controller.IBaseController;
import facebookdatamining.Controller.ProfileController;
import facebookdatamining.InfraStructure.Config;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Thiago N. Oliveira
 */
public class Main {

    private static final Config config = new Config();
    private static ExecutorService executorService = Executors.newFixedThreadPool(50);
    private static CompletionService<Object> completionService = new ExecutorCompletionService<>(executorService);
    private static Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        PropertyConfigurator.configure("config\\logConfiguration.properties");

        if (!(config == null)) {
            CreateThredsOfUsers();
        } else {
            log.warn("Could not read settings. Check the xml file.");
            System.exit(1);
        }
    }

    private static void CreateThredsOfUsers() {
        Set<String> keys = config.getAccounts().keySet();
        for (String currentKey : keys) {
            CreateThred(completionService, currentKey, (String) config.getAccounts().get(currentKey));
        }
    }

    private static void CreateThred(CompletionService<Object> cs, final String key, final String value) {
        cs.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(20000);
                IBaseController Controller = new ProfileController(config);
                Controller.extractInfo(key, value);
                return null;
            }
        });
    }
}