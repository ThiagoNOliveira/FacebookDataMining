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
        IBaseController Controller = new ProfileController();
        Controller.extractInfo(Id);
    }
}