package facebookdatamining;

import facebookdatamining.Controller.IBaseController;
import facebookdatamining.Controller.ProfileController;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Thiago N. Oliveira
 */
public class Main {

    private static long Id = 100003104984663L;

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(50);
        CompletionService<Object> cs = new ExecutorCompletionService<>(es);

        cs.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                IBaseController Controller = new ProfileController();
                Controller.extractInfo("pereirasilvaluana@yahoo.com.br", "leavemealone1");
                return null;
            }
        });

        cs.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(20000);
                IBaseController Controller = new ProfileController();
                Controller.extractInfo("anbudeneve@hotmail.com", "leavemealone");
                return null;
            }
        });

        cs.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(30000);
                IBaseController Controller = new ProfileController();
                Controller.extractInfo("rafael.nascimento42@yahoo.com.br", "leavemealone");
                return null;
            }
        });

        cs.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(40000);
                IBaseController Controller = new ProfileController();
                Controller.extractInfo("miguel.brito28@yahoo.com", "leavemealone");
                return null;
            }
        });
    }
}
