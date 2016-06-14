//package kaca;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Igrisce extends JPanel implements ActionListener, KeyListener {

    private final String KONEC_SPOROCILO = "Konec igre"; // sporoÄŤilo ob koncu igre
    private final int VEL_CELICE = 10; // velikost posamezne celice (v pikslih)
    private final int SIRINA = 30; // Ĺˇirina okna (v celicah)
    private final int VISINA = 30; // viĹˇina okna (v celicah)
    private final int POCAKAJ = 150; // ÄŤasovni interval med preverjanjem akcij uporabnika (v nanosekundah)
    private final int ZAC_ST_CLENOV = 3; // zaÄŤetno Ĺˇtevilo ÄŤlenov, ki jih ima kaÄŤa (glava + 2 ÄŤlena repa)

    // koordinate glave kaÄŤe (x[0] in y[0]) in njenega repa ((x[i] in y[i]), i > 0)
    // koordinate so koordinate celice (NE koordinate pikslov)
    // kaÄŤa je lahko dolga najveÄŤ toliko, kot je vseh celic na igralnem polju
    private int x[]; // x koordinate
    private int y[]; // y koordinate

    private int stClenov; // trenutna velikost kaÄŤe (Ĺˇtevilo ÄŤlenov)
    private int hrana_x;  // koordinata x celice, v kateri je postavljena hrana
    private int hrana_y;  // koordinata y celice, v kateri je postavljena hrana

    private boolean levo, desno, gor, dol; // smer premika; vedno je lahko TRUE le ena od naĹˇtetih smeri
    private boolean igra; // ali smo Ĺˇe v igri (true) ali smo Ĺľe konÄŤali (false)

    private Timer timer; // ÄŤasovnik, ki omogoÄŤa izvajanje animacije

    private BufferedImage ozadje; // slika, s katero podloĹľimo igriĹˇÄŤe (ozadje igriĹˇÄŤa)

    public Igrisce() {
        // zaÄŤetne nastavitve nove igre
        addKeyListener(this); // dodamo posliĹˇalca za dogodke s tipkovnice
        setBackground(Color.lightGray); // barva ozadja igralne povrĹˇine je siva
        setFocusable(true); // igralna povrĹˇina je lahko v fokusu
        zacniIgro(); // zaÄŤnemo novo igro
        this.stClenov = this.ZAC_ST_CLENOV;
    }

    public final void zacniIgro() {
        // zaÄŤnemo novo igro: inicializiramo spremenljivke

        x = new int[SIRINA*VISINA]; // najveÄŤja velikost kaÄŤe je enaka Ĺˇtevilu celic na igriĹˇÄŤu
        y = new int[SIRINA*VISINA];
        // TODO: nastavi zaÄŤetno velikost kaÄŤe in njeno zaÄŤetno pozicijo
        // zaÄŤetna pozicija kaÄŤe je poljubna (nakljuÄŤna),
        // kaÄŤa naj bo postavljena vodoravno in se premika v desno
        // (glavo ima na desni strani, rep sledi na levi)
        //
        // stClenov = ...
        // x[0] = ...
        // y[0] = ...
        
        x[0] = 14;
        y[0] = 14;
        x[1] = 14;
        y[1] = 14;
        x[2] = 14;
        y[2] = 14;

        // kaÄŤa se na zaÄŤetku premika proti desni
        gor = false;
        dol = false;
        levo = false;
        desno = true;

        postaviHrano(); // postavimo hrano na igralno povrĹˇino
        igra = true;    // smo v igri

        // postavimo ÄŤasovnik: v podanem ÄŤasevnem intervalu se sproĹľi
        // dogodek ActionEvent, ki ga obravnava ta razred (this)
        timer = new Timer(POCAKAJ, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        // izriĹˇi igralno povrĹˇino
        // ÄŤe igramo, izriĹˇi kaÄŤo in hrano
        // ÄŤe je igre konec, izpiĹˇi konÄŤno sporoÄŤilo na sredino igralne povrĹˇine

        super.paintComponent(g);
        if(igra) {
            // izrisi hrano za kaco - zlatnik
            // TODO

            // izrisi rdeco glavo kace
            // TODO
            g.setColor(Color.yellow);
            g.fillOval(hrana_x*this.VEL_CELICE,hrana_y*this.VEL_CELICE,this.VEL_CELICE, this.VEL_CELICE);
            for (int i = 1; i < this.stClenov; i++) {
                g.setColor(Color.green);
                g.fillOval(x[i]*this.VEL_CELICE, y[i]*this.VEL_CELICE, this.VEL_CELICE, this.VEL_CELICE);
            }
            
            g.setColor(Color.red);
            g.fillOval(x[0]*this.VEL_CELICE,y[0]*this.VEL_CELICE,this.VEL_CELICE, this.VEL_CELICE);
            // izrisi zelen rep kace
            // TODO

            // poskrbimo za posodobitev izrisa (potrebno za animacijo)
            Toolkit.getDefaultToolkit().sync();
            g.dispose();
        } else {
            // izpiĹˇi sporoÄŤilo ob koncu igre
            Font pisava = new Font("Helvetica", Font.BOLD, 18);
            FontMetrics metr = this.getFontMetrics(pisava);
            int dolzinaNiza = metr.stringWidth(KONEC_SPOROCILO);
            g.setColor(Color.black);
            g.setFont(pisava);
            // TODO: poskrbite za izris niza

        }
    }

    public void preveriHrano() {
        // preveri, ali je kaÄŤa pojedla hrano
        // (kaÄŤa poje hrano, ko se koordinate glave kaÄŤe pokrijejo s koordinatami hrane)
        // ÄŤe je, poveÄŤaj njeno velikost in postavi novo hrano

        // TODO
        if(hrana_x == x[0] && hrana_y == y[0]){
            this.stClenov++;
            this.postaviHrano();
            
        }

    }

    public void premakni() {
        // poskrbimo za ustrezen premik kaÄŤe
        for (int i = this.stClenov; i>=0; i--) {
            x[i+1] = x[i];    
            y[i+1] = y[i];
        }
        if(this.desno){
            x[0]++;
        }
        // premakni cel rep kaÄŤe
        // TODO
        if(this.levo)
            x[0]--;
        if(this.gor){
            y[0]--;
        }
        if(this.dol)
            y[0]++;

        // premakni glavo kaÄŤe
        // TODO
        // if(levo)    // ÄŤe se glava kaÄŤe premika v levo, se Ĺˇtevilka celice x koordinate glave zmanjĹˇuje
        //     x[0]--;
        // .....

    }

    public void preveriTrk() {
        // preverimo, ali je kaÄŤa zadela rob igriĹˇÄŤa ali samo sebe

        // TODO
        // if(x[0] < 0)       // kaÄŤa je zadela levi rob igriĹˇÄŤa
        //     igra = false;  // konÄŤamo igro
        // ...
        if(x[0] < 0 || y[0] < 0 || x[0] > this.SIRINA || y[0] > this.VISINA)
            igra = false;


    }

    public void postaviHrano() {
        Random rd = new Random();
        // doloÄŤimo lokacijo hrane

        // nastavi novo lokacijo hrane na neko nakljuÄŤno vrednost
        // (ki pa mora biti znotraj igriĹˇÄŤa)
        // TODO
        // hrana_x = ...
        // hrana_y = ...
           hrana_x = rd.nextInt(this.SIRINA);
           hrana_y = rd.nextInt(this.VISINA);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // glavna zanka - kaj delamo
        if(igra) { // ÄŤe smo Ĺˇe v igri
            preveriHrano();
            premakni();
            preveriTrk();
        }
        if(!igra) // igra se je zakljuÄŤila, ustavimo timer
            timer.stop();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // pogledamo, katero tipko je pritisnil uporabnik in ustrezno ukrepamo
        // zanimajo nas puĹˇÄŤice (gor, dol, levo, desno) in Enter (zaÄŤetek igre)
        // ESC je zakljuÄŤek programa

        int key = e.getKeyCode();
        if( key == KeyEvent.VK_LEFT && !desno ) {
            levo = true;
            gor = false;
            dol = false;
            // ustrezno reagiraj na tipko levo
            // TODO: kaÄŤa se po novem premika v levo
            // levo = true;
            // gor = false;
            // dol = false;
        }
        else if(key == KeyEvent.VK_RIGHT && !levo){
            desno = true;
            gor = false;
            dol = false;
        }
        else if(key == KeyEvent.VK_UP && !dol){
            gor = true;
            levo = false;
            desno = false;
        }
        else if(key == KeyEvent.VK_DOWN && !gor){
            dol = true;
            levo = false;
            desno = false;
        }

        // reakcije na druge tipke
        // TODO

        if( !igra && key == KeyEvent.VK_ESCAPE ) {
            // TODO: konÄŤaj program
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

}