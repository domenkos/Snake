//package kaca;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Implementacija igrice Kaca
 *
 * @author alenka
 */
public class Kaca {

    private final static int VEL = 315; // velikost okna (okno naj bi bilo kvadratne oblike)
    private final static int VEL_NASLOVA = 20; // velikost naslovne vrstice, ki jo zavzame okvir okna

    // ustvarjanje uporabniĹˇkega vmesnika
    private static void ustvariGUI() {
        // ustvarimo novo okno z naslovom "KaÄŤa"
        JFrame okno = new JFrame("KaÄŤa");
        // velikost okna nastavi na doloÄŤeno velikost
        okno.setSize(VEL, VEL+VEL_NASLOVA);
        // onemogoÄŤi spreminjanje velikosti okna
        okno.setResizable(false);
        // ÄŤe okno zapremo (klik na x), se program konÄŤa
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // v okno postavimo nov objekt razreda Igrisce (ta razred je bistvo naĹˇega programa)
        okno.add(new Igrisce());
        // okno prikaĹľemo na zaslonu
        okno.setVisible(true);
    }

    public static void main(String[] args) {
        // v ustrezni niti pokliÄŤemo metodo, ki ustvari GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ustvariGUI();
            }
        });
    }
}