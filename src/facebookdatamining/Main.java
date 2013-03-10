package facebookdatamining;

import facebookdatamining.Controller.IBaseController;
import facebookdatamining.Controller.ProfileController;

/**
 *
 * @author Thiago N. Oliveira
 */
public class Main {

    private static long Id = 100003104984663L;

    public static void main(String[] args) {
        //ExecutorService es = Executors.newFixedThreadPool(50);
        //CompletionService<Object> cs = new ExecutorCompletionService<>(es);
        //for (int i = 1; i <= 50; i++) {
        //  cs.submit(new Callable<Object>() {
        //    @Override
        //  public Object call() throws Exception {
        //    java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        IBaseController Controller = new ProfileController();
        Controller.extractInfo();
        //  return null;
        //      }
        //  });
        // }
    }
}
