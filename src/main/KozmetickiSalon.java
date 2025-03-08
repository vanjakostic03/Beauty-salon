package main;

import manager.AppManager;
import utils.AppSettigs;
import view.mainFrame;

public class KozmetickiSalon {
    public static void main(String[] args) {

        System.out.println("Loading....");
        AppSettigs as = new AppSettigs("./data/klijenti.csv", "./data/recepcioneri.csv", "./data/kozmeticari.csv", "./data/menadzeri.csv","./data/cenovnik.csv","./data/tretmani.csv","./data/vrstetretmana.csv","./data/tiptretmana.csv","./data/saloninfo.csv");
        AppManager appMng = new AppManager(as);
        appMng.loadData();
        mainFrame main = new mainFrame(appMng);
        main.toString();
    }
}
