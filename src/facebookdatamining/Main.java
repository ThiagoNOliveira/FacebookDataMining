package facebookdatamining;

import facebookdatamining.Controller.ProfileController;
import java.io.IOException;

/**
 *
 * @author Thiago N. Oliveira
 */
public class Main {
    private static long Id = 100003104984663L;

    public static void main(String[] args) throws IOException, InterruptedException {
        ProfileController controller = new ProfileController();
        controller.extractInfo(Id);
    }
}