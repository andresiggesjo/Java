/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inlupp23;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import javax.swing.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Layout extends JFrame implements ActionListener, MouseListener {

    private ArrayList<Kategori> kategorier = new ArrayList<Kategori>();
    private ArrayList<String> infolist = new ArrayList<>();
    private HashMap<String, ArrayList<Platser>> positioner = new HashMap<String, ArrayList<Platser>>();
    private HashMap<Position, Platser> finnsHär = new HashMap<>();
    private ArrayList<Position> pos = new ArrayList<>();
    protected JColorChooser jcc;
    private JPanel felPanel = new JPanel();
    private boolean sparad = false;
    private boolean skapad = false;
    private boolean klickad = false;
    private boolean någotÄndrat = false;
    private boolean visaMap = false;
    private boolean vadFinnsHär = false;

    private String kartVäg;
    private String sparnamn;
    private String filVäg;
    private JMenuBar menuBar;
    private JMenu arkiv;
    private JMenuItem newMap, open, save, exit;
    private JButton hideCategoryButton, newCategoryButton,
            deleteCategoryButton, searchButton, hideButton, deleteButton,
            whatsHereButton;
    private JComboBox<?> comboBox;
    private JTextField sökfält;
    private JList<String> list;
    private JLabel ny, kategory, bild;
    private String[] comboboxalternativ = {"Gör ett val", "Described place",
        "Named place"};
    private DefaultListModel<String> mod1 = new DefaultListModel<String>();
    private JScrollPane scroller;
    private ImageIcon karta;
    private JPanel panelen;

    public Layout() {

        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Prog2Inlupp2");

        panelen = new JPanel();
        panelen.setLayout(new BorderLayout());

        // JMENUBAR
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        arkiv = new JMenu("Arkiv");
        menuBar.add(arkiv);
        newMap = new JMenuItem("New map");
        arkiv.add(newMap);
        open = new JMenuItem("Open");
        arkiv.add(open);
        save = new JMenuItem("Save");
        arkiv.add(save);
        exit = new JMenuItem("Exit");
        arkiv.add(exit);

        // ALLA LABELS
        ny = new JLabel("Ny:");
        kategory = new JLabel("Categories");

        // COMBOBOXEN
        comboBox = new JComboBox<Object>(comboboxalternativ);

        // SÖKFÄLTET
        sökfält = new JTextField("Search", 10);

        // ALLA KNAPPAR
        hideCategoryButton = new JButton("Hide category");
        newCategoryButton = new JButton("New category");
        deleteCategoryButton = new JButton("Delete category");
        searchButton = new JButton("Search");
        hideButton = new JButton("Hide places");
        deleteButton = new JButton("Delete places");
        whatsHereButton = new JButton("What is here?");

        // JLISTAN
        list = new JList<String>(mod1);
        list.setVisibleRowCount(10);

        scroller = new JScrollPane(list,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        list.setFixedCellHeight(20);
        list.setFixedCellWidth(150);

        // NORRA KNAPPARNA
        JPanel topKnappar = new JPanel();
        topKnappar.setLayout(new FlowLayout(FlowLayout.LEFT));
        topKnappar.add(ny);
        topKnappar.add(comboBox);
        topKnappar.add(sökfält);
        topKnappar.add(searchButton);
        topKnappar.add(hideButton);
        topKnappar.add(deleteButton);
        topKnappar.add(whatsHereButton);

        panelen.add(topKnappar, BorderLayout.NORTH);

        // ÖSTRA KNAPPARNA
        JPanel eastKnappar = new JPanel();

        Box theBox = Box.createVerticalBox();
        theBox.add(Box.createVerticalStrut(110));
        theBox.add(kategory);
        theBox.add(scroller);
        theBox.add(Box.createVerticalStrut(5));
        theBox.add(hideCategoryButton);
        theBox.add(Box.createVerticalStrut(5));
        theBox.add(newCategoryButton);
        theBox.add(Box.createVerticalStrut(5));
        theBox.add(deleteCategoryButton);
        eastKnappar.add(theBox);
        panelen.add(eastKnappar, BorderLayout.EAST);
        // thePanel.addMouseListener(ml);

        save.addActionListener(this);
        newMap.addActionListener(this);
        newCategoryButton.addActionListener(this);
        comboBox.addActionListener(this);
        searchButton.addActionListener(this);
        open.addActionListener(this);
        exit.addActionListener(this);
        deleteCategoryButton.addActionListener(this);
        hideButton.addActionListener(this);
        deleteButton.addActionListener(this);
        hideCategoryButton.addActionListener(this);
        list.addMouseListener(this);
        whatsHereButton.addActionListener(this);

        this.add(panelen);
        this.setVisible(true);

        list.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if (list.isSelectedIndex(index0)) {
                    list.removeSelectionInterval(index0, index1);
                } else {
                    list.addSelectionInterval(index0, index1);
                }
            }
        });

    }

    //Ber om ursäkt för att vi inte la in nedanstående kod i metoder.
    //Ber också om ursäkt för upprepande kod.
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == whatsHereButton) {
            bild.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            bild.addMouseListener(this);
            vadFinnsHär = true;

        }

        if (e.getSource() == hideCategoryButton) {
            if (!list.isSelectionEmpty()) {

                for (Map.Entry<String, ArrayList<Platser>> entrySet : positioner
                        .entrySet()) {
                    ArrayList<Platser> platserna = entrySet.getValue();
                    for (Platser platserna1 : platserna) {
                        try{
                        if (platserna1.getKategori().getNamn().equals(list.getSelectedValue())) {
                            platserna1.getTriangel().setVisible(false);
                            platserna1.getTriangel().setVisas(false);
                        }
                        }catch (NullPointerException npe){
                            
                        }
                    }
                }

            }
        }

        if (e.getSource() == hideButton) {

            for (Map.Entry<String, ArrayList<Platser>> entrySet : positioner
                    .entrySet()) {
                // String namn = entrySet.getKey();
                ArrayList<Platser> platserna = entrySet.getValue();

                for (Platser platserna1 : platserna) {

                    boolean a = platserna1.getTriangel().isMarkerad();
                    if (a) {
                        platserna1.getTriangel().setMarkerad(false);
                        platserna1.getTriangel().setVisible(false);
                        platserna1.getTriangel().setVisas(false);

                    }

                }

            }

        }

        if (e.getSource() == deleteButton) {

            HashMap<String, ArrayList<Platser>> temppos = new HashMap<String, ArrayList<Platser>>();
            for (Map.Entry<String, ArrayList<Platser>> entrySet : positioner.entrySet()) {
                String key = entrySet.getKey();

                ArrayList<Platser> platserna = entrySet.getValue();
                ArrayList<Platser> tempplatser = new ArrayList<Platser>();
                for (int i = 0; i < platserna.size(); i++) {
                    if (!platserna.get(i).getTriangel().isMarkerad()) {
                        tempplatser.add(platserna.get(i));
                    } else {
                        platserna.get(i).setVisible(false);
                        bild.remove(platserna.get(i).getTriangel());
                    }

                }
                if (!tempplatser.isEmpty()) {
                    temppos.put(key, tempplatser);
                    
                }

                /*Iterator<Platser> itx = platserna.iterator();

                 while (itx.hasNext()) {
                 Platser plats = itx.next();
                 if (plats.getTriangel().isMarkerad()) {
                 plats.getTriangel().setVisible(false);
                 itx.remove();
                 if (platserna.isEmpty()) {
                 // positioner.remove(plats.getNamn()); // seru den här kra
                 positioner.remove(key);
                 }
                 }
                 }*/
            }
            positioner.clear();
            positioner = temppos;
            this.bild.removeAll();
            for (Map.Entry<String, ArrayList<Platser>> entrySet : positioner.entrySet()) {
                String key = entrySet.getKey();
                ArrayList<Platser> value = entrySet.getValue();
                drawDelete(value);
            }
            this.bild.repaint();

        }

        if (e.getSource() == deleteCategoryButton) {
            HashMap<String, ArrayList<Platser>> temppos = new HashMap<String, ArrayList<Platser>>();
            if (!list.isSelectionEmpty()) {
                int index = list.getSelectedIndex();
                
                for (Map.Entry<String, ArrayList<Platser>> entrySet : positioner.entrySet()) {
                    String key = entrySet.getKey();

                    ArrayList<Platser> platserna = entrySet.getValue();
                    

                    ArrayList<Platser> tempplatser = new ArrayList<Platser>();
                    for (int i = 0; i < platserna.size(); i++) {
                      
                        if(platserna.get(i).getKategori() == null || platserna.get(i).getKategori().getNamn() == null || platserna.get(i).getKategori().getNamn().equalsIgnoreCase("null")){
                                tempplatser.add(platserna.get(i));
                                
                        }else {
                           
                            
                           
                            if (!platserna.get(i).getKategori().getNamn().equalsIgnoreCase(list.getSelectedValue())) {
                            tempplatser.add(platserna.get(i));
                            
                            } else {
                                platserna.get(i).setVisible(false);
                                bild.remove(platserna.get(i).getTriangel());
                            }
                        }
                   
                       
                    }
                    if (!tempplatser.isEmpty()) {
                        temppos.put(key, tempplatser);
                        
                    }
      
                }

                for (int i = 0; i < kategorier.size(); i++) {
                    if (list.getSelectedValue().equals(
                            kategorier.get(i).getNamn())) {

                        kategorier.remove(i);
                    }
                }

                mod1.remove(index);

            }

            positioner.clear();
            positioner = temppos;
            this.bild.removeAll();
            for (Map.Entry<String, ArrayList<Platser>> entrySet : positioner.entrySet()) {
                String key = entrySet.getKey();
                ArrayList<Platser> value = entrySet.getValue();
                for (Platser value1 : value) {
                    if(value1.getTriangel().isVisas()){
                      drawDelete(value);
                    }
                    
                }
                
            }
           
            this.bild.repaint();
        }

        if (e.getSource() == exit) {

            if (någotÄndrat) {
                int result = JOptionPane.showConfirmDialog(felPanel,
                        "Du har osparade förändringar, vill du fortsätta?",
                        "Fel!", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }

            } else {
                System.exit(0);
            }
        }

        if (e.getSource() == open) {

            // RENSA ALL GAMMAL DATA
            if (någotÄndrat) {
                int result = JOptionPane.showConfirmDialog(felPanel,
                        "Du har osparade förändringar, vill du fortsätta?",
                        "Fel!", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    panelen.remove(bild);
                    for (Map.Entry<String, ArrayList<Platser>> entrySet : positioner
                            .entrySet()) {
                        ArrayList<Platser> platserna = entrySet.getValue();
                        platserna.clear();
                        kategorier.clear();
                        infolist.clear();
                        mod1.clear();
                        positioner.clear();

                    }

                    JFileChooser filVäljare = new JFileChooser();
                    if (filVäljare.showOpenDialog(Layout.this) == JFileChooser.APPROVE_OPTION) {
                        File f = filVäljare.getSelectedFile();
                        FileReader fr;
                        try {
                            fr = new FileReader(f);
                            BufferedReader br = new BufferedReader(fr);

                            String rad;

                            while ((rad = br.readLine()) != null) {

                                String[] tempArray = rad.split(",");
                                infolist.addAll(Arrays.asList(tempArray));

                            }
                            bild = new JLabel(new ImageIcon(kartVäg));
                            bild.addMouseListener(this);
                            panelen.add(bild, BorderLayout.WEST);
                            visaMap = true;
                            pack();
                            validate();

                            infolist.remove(0);
                            ListIterator<String> listIterator = infolist
                                    .listIterator();

                            for (int i = 0; i < infolist.size(); i++) {

                                if (infolist.get(1).equals("null")) {
                                    NamedPlaces rcp = new NamedPlaces();
                                    rcp.setNamn(infolist.get(0));
                                    Kategori rk = new Kategori(infolist.get(2));
                                    rcp.setKategori(rk);
                                    int färgint = Integer.parseInt(infolist
                                            .get(3));
                                    Color c = new Color(färgint);
                                    rcp.getKategori().setFärg(c);
                                    int x = Integer.parseInt(infolist.get(4));
                                    int y = Integer.parseInt(infolist.get(5));
                                    Position p = new Position(x, y);
                                    rcp.setPosition(p);
                                    rcp.getPosition().x = p.x;
                                    rcp.getPosition().y = p.y;
                                    Triangel t = new Triangel(
                                            rcp.getPosition().x,
                                            rcp.getPosition().y, rcp
                                            .getKategori().getFärg(),
                                            rcp.getNamn());
                                    bild.add(t);
                                    rcp.setTriangel(t);
                                    this.repaint();

                                    ArrayList<Platser> tempAp = new ArrayList<>();
                                    tempAp.add(rcp);
                                    finnsHär.put(rcp.getPosition(), rcp);
                                    if (!rcp.getKategori().getNamn()
                                            .equals("null")) {
                                        if (!mod1.contains(rcp.getKategori()
                                                .getNamn())) {
                                            mod1.addElement(rcp.getKategori()
                                                    .getNamn());
                                            kategorier.add(rcp.getKategori());
                                        }
                                    }

                                    positioner.put(rcp.getNamn(), tempAp);
                                    try {
                                        for (int j = 0; j < 6; j++) {

                                            listIterator.next();
                                            drawTriangels(tempAp);
                                            listIterator.remove();

                                        }
                                    } catch (IndexOutOfBoundsException ex) {

                                    }

                                } else if (!infolist.get(1).equals("null")) {
                                    DescribedPlaces rcp = new DescribedPlaces();
                                    rcp.setNamn(infolist.get(0));
                                    rcp.beskrivning = infolist.get(1);
                                    Kategori rk = new Kategori(infolist.get(2));
                                    rcp.setKategori(rk);
                                    int färgint = Integer.parseInt(infolist
                                            .get(3));
                                    Color c = new Color(färgint);
                                    rcp.getKategori().setFärg(c);
                                    int x = Integer.parseInt(infolist.get(4));
                                    int y = Integer.parseInt(infolist.get(5));
                                    Position p = new Position(x, y);
                                    rcp.setPosition(p);
                                    rcp.getPosition().x = p.x;
                                    rcp.getPosition().y = p.y;
                                    Triangel t = new Triangel(
                                            rcp.getPosition().x,
                                            rcp.getPosition().y, rcp
                                            .getKategori().getFärg(),
                                            rcp.getBeskrivning());
                                    bild.add(t);
                                    rcp.setTriangel(t);
                                    this.repaint();
                                    if (rcp.beskrivning.equals("null")) {
                                        rcp.beskrivning = null;
                                    }
                                    ArrayList<Platser> tempAp = new ArrayList<>();
                                    tempAp.add(rcp);
                                    finnsHär.put(rcp.getPosition(), rcp);
                                    if (!rcp.getKategori().getNamn()
                                            .equals("null")) {
                                        if (!mod1.contains(rcp.getKategori()
                                                .getNamn())) {
                                            mod1.addElement(rcp.getKategori()
                                                    .getNamn());
                                            kategorier.add(rcp.getKategori());
                                        }

                                    }

                                    positioner.put(rcp.getNamn(), tempAp);

                                    for (int j = 0; j < 6; j++) {

                                        listIterator.next();
                                        drawTriangels(tempAp);
                                        listIterator.remove();

                                    }

                                }
                            }

                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(Layout.class.getName()).log(
                                    Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(Layout.class.getName()).log(
                                    Level.SEVERE, null, ex);
                        }
                    }
                }

            } else if (!någotÄndrat) {
                for (Map.Entry<String, ArrayList<Platser>> entrySet : positioner
                        .entrySet()) {
                    ArrayList<Platser> platserna = entrySet.getValue();
                    platserna.clear();
                    kategorier.clear();
                    infolist.clear();
                    mod1.clear();
                    positioner.clear();

                }
                JFileChooser filVäljare = new JFileChooser();
                if (filVäljare.showOpenDialog(Layout.this) == JFileChooser.APPROVE_OPTION) {
                    File f = filVäljare.getSelectedFile();
                    FileReader fr;
                    try {
                        fr = new FileReader(f);
                        BufferedReader br = new BufferedReader(fr);

                        String line;

                        while ((line = br.readLine()) != null) {

                            String[] tempArray = line.split(",");
                            infolist.addAll(Arrays.asList(tempArray));

                        }
                        filVäg = infolist.get(0);
                        infolist.remove(0);
                        ListIterator<String> listIterator = infolist
                                .listIterator();

                        for (int i = 0; i < infolist.size(); i++) {

                            if (infolist.get(1).equals("null")) {

                                NamedPlaces rcp = new NamedPlaces();
                                rcp.setNamn(infolist.get(0));
                                Kategori rk = new Kategori(infolist.get(2));
                                rcp.setKategori(rk);
                                int färgint = Integer.parseInt(infolist.get(3));
                                Color c = new Color(färgint);
                                rcp.getKategori().setFärg(c);
                                int x = Integer.parseInt(infolist.get(4));
                                int y = Integer.parseInt(infolist.get(5));
                                Position p = new Position(x, y);
                                rcp.setPosition(p);
                                rcp.getPosition().x = p.x;
                                rcp.getPosition().y = p.y;
                                Triangel t = new Triangel(rcp.getPosition().x,
                                        rcp.getPosition().y, rcp.getKategori()
                                        .getFärg(), rcp.getNamn());
                                bild.add(t);
                                rcp.setTriangel(t);
                                this.repaint();

                                ArrayList<Platser> tempAp = new ArrayList<>();
                                tempAp.add(rcp);
                                finnsHär.put(rcp.getPosition(), rcp);
                                if (!rcp.getKategori().getNamn().equals("null")) {
                                    if (!mod1.contains(rcp.getKategori()
                                            .getNamn())) {
                                        mod1.addElement(rcp.getKategori()
                                                .getNamn());
                                        kategorier.add(rcp.getKategori());
                                    }
                                }

                                positioner.put(rcp.getNamn(), tempAp);
                                try {
                                    for (int j = 0; j < 6; j++) {

                                        listIterator.next();
                                        drawTriangels(tempAp);
                                        listIterator.remove();

                                    }
                                } catch (IndexOutOfBoundsException ex) {

                                }

                            } else if (!infolist.get(1).equals("null")) {
                                DescribedPlaces rcp = new DescribedPlaces();
                                rcp.setNamn(infolist.get(0));
                                rcp.beskrivning = infolist.get(1);
                                Kategori rk = new Kategori(infolist.get(2));
                                rcp.setKategori(rk);
                                int färgint = Integer.parseInt(infolist.get(3));
                                Color c = new Color(färgint);
                                rcp.getKategori().setFärg(c);
                                int x = Integer.parseInt(infolist.get(4));
                                int y = Integer.parseInt(infolist.get(5));
                                Position p = new Position(x, y);
                                rcp.setPosition(p);
                                rcp.getPosition().x = p.x;
                                rcp.getPosition().y = p.y;
                                Triangel t = new Triangel(rcp.getPosition().x,
                                        rcp.getPosition().y, rcp.getKategori()
                                        .getFärg(),
                                        rcp.getBeskrivning());
                                bild.add(t);
                                rcp.setTriangel(t);
                                this.repaint();
                                if (rcp.beskrivning.equals("null")) {
                                    rcp.beskrivning = null;
                                }
                                ArrayList<Platser> tempAp = new ArrayList<>();
                                tempAp.add(rcp);
                                finnsHär.put(rcp.getPosition(), rcp);
                                if (!rcp.getKategori().getNamn().equals("null")) {
                                    if (!mod1.contains(rcp.getKategori()
                                            .getNamn())) {
                                        mod1.addElement(rcp.getKategori()
                                                .getNamn());
                                        kategorier.add(rcp.getKategori());
                                    }

                                }

                                positioner.put(rcp.getNamn(), tempAp);

                                try {
                                    for (int j = 0; j < 6; j++) {

                                        listIterator.next();
                                        drawTriangels(tempAp);
                                        listIterator.remove();

                                    }

                                } catch (IndexOutOfBoundsException ex) {

                                }
                            }

                        }

                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Layout.class.getName()).log(
                                Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Layout.class.getName()).log(
                                Level.SEVERE, null, ex);
                    }
                }
            }
        }

        if (e.getSource() == newMap) {
            if (någotÄndrat) {

                int result = JOptionPane.showConfirmDialog(felPanel,
                        "Du har osparade förändringar, vill du fortsätta?",
                        "Fel!", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    for (Map.Entry<String, ArrayList<Platser>> entrySet : positioner
                            .entrySet()) {

                        ArrayList<Platser> platserna = entrySet.getValue();
                        platserna.clear();
                        kategorier.clear();
                        infolist.clear();
                        mod1.clear();
                        positioner.clear();
                    }
                    någotÄndrat = false;
                    if (visaMap) {
                        panelen.remove(bild);
                        JFileChooser filVäljare = new JFileChooser();
                        filVäljare
                                .setFileSelectionMode(JFileChooser.FILES_ONLY);
                        filVäljare.showOpenDialog(Layout.this);
                        File file = filVäljare.getSelectedFile();
                        kartVäg = file.getAbsolutePath();
                        bild = new JLabel(new ImageIcon(kartVäg));
                        bild.addMouseListener(this);
                        panelen.add(bild, BorderLayout.WEST);
                        visaMap = true;
                        pack();
                        validate();
                    } else if (!visaMap) {
                        JFileChooser filVäljare = new JFileChooser();
                        filVäljare
                                .setFileSelectionMode(JFileChooser.FILES_ONLY);
                        filVäljare.showOpenDialog(Layout.this);
                        File file = filVäljare.getSelectedFile();
                        kartVäg = file.getAbsolutePath();
                        bild = new JLabel(new ImageIcon(kartVäg));
                        bild.addMouseListener(this);
                        panelen.add(bild, BorderLayout.WEST);
                        visaMap = true;
                        pack();
                        validate();
                    }

                }

            } else if (!någotÄndrat) {
                for (Map.Entry<String, ArrayList<Platser>> entrySet : positioner
                        .entrySet()) {
                    ArrayList<Platser> platserna = entrySet.getValue();
                    platserna.clear();
                    kategorier.clear();
                    infolist.clear();
                    mod1.clear();
                    positioner.clear();

                }
                if (visaMap) {
                    panelen.remove(bild);
                    JFileChooser filVäljare = new JFileChooser();
                    filVäljare.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    filVäljare.showOpenDialog(Layout.this);
                    File file = filVäljare.getSelectedFile();
                    kartVäg = file.getAbsolutePath();
                    bild = new JLabel(new ImageIcon(kartVäg));
                    bild.addMouseListener(this);
                    panelen.add(bild, BorderLayout.WEST);
                    visaMap = true;
                    pack();
                    validate();
                } else if (!visaMap) {
                    JFileChooser filVäljare = new JFileChooser();
                    filVäljare.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    filVäljare.showOpenDialog(Layout.this);
                    File file = filVäljare.getSelectedFile();
                    kartVäg = file.getAbsolutePath();
                    bild = new JLabel(new ImageIcon(kartVäg));
                    bild.addMouseListener(this);
                    panelen.add(bild, BorderLayout.WEST);
                    visaMap = true;
                    pack();
                    validate();
                }
            }
        }
        if (e.getSource() == save) {

            if (sparad == false) {
                JFileChooser filVäljare = new JFileChooser();
                filVäljare.setDialogTitle("Specify a file to save");
                filVäljare.showSaveDialog(Layout.this);
                sparnamn = filVäljare.getSelectedFile().getName();

                try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(filVäljare.getSelectedFile()),
                        "utf-8"))) {

                    try {
                        filVäg = filVäljare.getSelectedFile()
                                .getCanonicalPath();

                    } catch (IOException ex) {
                        Logger.getLogger(Layout.class.getName()).log(
                                Level.SEVERE, null, ex);
                    }
                    String contentToSave = "";
                    contentToSave = "Path to map: " + kartVäg;

                    writer.write(contentToSave + System.lineSeparator());

                    for (Map.Entry<String, ArrayList<Platser>> entrySet : positioner
                            .entrySet()) {
                        ArrayList<Platser> platserna = entrySet.getValue();

                        for (Platser platserna1 : platserna) {
                            if (platserna1 instanceof DescribedPlaces) {
                                DescribedPlaces dp = (DescribedPlaces) platserna1;
                                contentToSave = dp.getNamn() + ",";
                                contentToSave += dp.beskrivning + ",";
                                if (platserna1.getKategori().getNamn() != null) {

                                    contentToSave += dp.getKategori().getNamn()
                                            + ",";
                                    contentToSave += dp.getKategori().getFärg()
                                            .getRGB()
                                            + ",";
                                    contentToSave += dp.getPosition().x + ","
                                            + dp.getPosition().y;
                                    writer.write(contentToSave
                                            + System.lineSeparator());
                                } else {
                                    contentToSave += "null" + ",";
                                    contentToSave += dp.getKategori().getFärg()
                                            .getRGB()
                                            + ",";
                                    contentToSave += dp.getPosition().x + ","
                                            + dp.getPosition().y;
                                    writer.write(contentToSave
                                            + System.lineSeparator());
                                }
                            } else if (platserna1 instanceof NamedPlaces) {
                                NamedPlaces np = (NamedPlaces) platserna1;
                                contentToSave = np.getNamn() + ",";
                                contentToSave += "null" + ",";
                                if (platserna1.getKategori().getNamn() != null) {

                                    contentToSave += np.getKategori().getNamn()
                                            + ",";
                                    contentToSave += np.getKategori().getFärg()
                                            .getRGB()
                                            + ",";
                                    contentToSave += np.getPosition().x + ","
                                            + np.getPosition().y;
                                    writer.write(contentToSave
                                            + System.lineSeparator());
                                } else {
                                    contentToSave += "null" + ",";
                                    contentToSave += np.getKategori().getFärg()
                                            .getRGB()
                                            + ",";
                                    contentToSave += np.getPosition().x + ","
                                            + np.getPosition().y;
                                    writer.write(contentToSave
                                            + System.lineSeparator());
                                }
                            }

                        }

                    }

                } catch (IOException ex) {

                }
                sparad = true;
                någotÄndrat = false;
            } else if (sparad == true) {
                String contentToSave = "";
                contentToSave = kartVäg;
                try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(filVäg), "utf-8"))) {
                    writer.write(contentToSave + System.lineSeparator());

                    for (Map.Entry<String, ArrayList<Platser>> entrySet : positioner
                            .entrySet()) {
                        ArrayList<Platser> platserna = entrySet.getValue();

                        for (Platser platserna1 : platserna) {
                            if (platserna1 instanceof DescribedPlaces) {
                                DescribedPlaces dp = (DescribedPlaces) platserna1;
                                contentToSave = dp.getNamn() + ",";
                                contentToSave += dp.beskrivning + ",";
                                if (platserna1.getKategori().getNamn() != null) {

                                    contentToSave += dp.getKategori().getNamn()
                                            + ",";
                                    contentToSave += dp.getKategori().getFärg()
                                            .getRGB()
                                            + ",";
                                    contentToSave += dp.getPosition().x + ","
                                            + dp.getPosition().y;
                                    writer.write(contentToSave
                                            + System.lineSeparator());
                                } else {
                                    contentToSave += "null" + ",";
                                    contentToSave += dp.getKategori().getFärg()
                                            .getRGB()
                                            + ",";
                                    contentToSave += dp.getPosition().x + ","
                                            + dp.getPosition().y;
                                    writer.write(contentToSave
                                            + System.lineSeparator());
                                }
                            } else if (platserna1 instanceof NamedPlaces) {
                                NamedPlaces np = (NamedPlaces) platserna1;
                                contentToSave = np.getNamn() + ",";
                                contentToSave += "null" + ",";
                                if (platserna1.getKategori().getNamn() != null) {

                                    contentToSave += np.getKategori().getNamn()
                                            + ",";
                                    contentToSave += np.getKategori().getFärg()
                                            .getRGB()
                                            + ",";
                                    contentToSave += np.getPosition().x + ","
                                            + np.getPosition().y;
                                    writer.write(contentToSave
                                            + System.lineSeparator());
                                } else {
                                    contentToSave += "null" + ",";
                                    contentToSave += np.getKategori().getFärg()
                                            .getRGB()
                                            + ",";
                                    contentToSave += np.getPosition().x + ","
                                            + np.getPosition().y;
                                    writer.write(contentToSave
                                            + System.lineSeparator());
                                }
                            }

                        }

                    }

                } catch (IOException ex) {

                }
                någotÄndrat = false;
            }
        }

        if (e.getSource() == newCategoryButton) {

            JPanel msgPanel = new JPanel();
            JPanel panelOnMsgPanel = new JPanel(new FlowLayout());
            JLabel namns = new JLabel("Namn: ");
            msgPanel.setLayout(new BorderLayout());
            JTextField xField = new JTextField(10);

            panelOnMsgPanel.add(namns);
            panelOnMsgPanel.add(xField);

            msgPanel.add(panelOnMsgPanel, BorderLayout.NORTH);

            jcc = new JColorChooser();
            msgPanel.add(jcc, BorderLayout.SOUTH);
            int result = JOptionPane.showConfirmDialog(null, msgPanel,
                    "Ny Kategori", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    for (Kategori kategorier1 : kategorier) {
                        if (kategorier1.getNamn().equals(xField.getText())) {
                            throw new Exception();
                        }
                    }

                    Kategori ett = new Kategori(null);
                    try {
                        if (xField.getText().matches("[-+]?\\d*\\.?\\d+")) {
                            throw new Exception();
                        }

                        ett.setNamn(xField.getText());
                        ett.setFärg(jcc.getColor());

                        kategorier.add(ett);
                        mod1.removeAllElements();
                        for (Kategori kat : kategorier) {
                            mod1.addElement(kat.getNamn());

                        }

                    } catch (Exception exc) {
                        JOptionPane.showMessageDialog(felPanel, "Fel inmatning!",
                                "Fel!", JOptionPane.ERROR_MESSAGE);

                    }
                } catch (Exception finnsredan) {
                    JOptionPane.showMessageDialog(felPanel, "Det finns redan en kategori med det här namnet!",
                            "Fel!", JOptionPane.ERROR_MESSAGE);
                }

            }
            någotÄndrat = true;
        }

        if (e.getSource() == searchButton) {
            showResult(sökfält.getText());
        }
        if (comboBox.getSelectedIndex() == 2) {
            bild.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
        if (comboBox.getSelectedIndex() == 1) {
            bild.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }

    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (!list.isSelectionEmpty()) {
           
            for (Map.Entry<String, ArrayList<Platser>> entrySet : positioner.entrySet()) {
                String key = entrySet.getKey();
                ArrayList<Platser> platserna = entrySet.getValue();
                for (int i = 0; i < platserna.size(); i++) {
                    
                    if(platserna.get(i).getKategori() == null || platserna.get(i).getKategori().getNamn() == null || platserna.get(i).getKategori().getNamn().equalsIgnoreCase("null")){
                    
                    }
                    else{
                         if(platserna.get(i).getKategori().getNamn().equalsIgnoreCase(list.getSelectedValue())){
                        
                        platserna.get(i).getTriangel().setVisible(true);
                        platserna.get(i).getTriangel().setVisas(true);
                    }    
                    }
                   
                }
               
            }

        }

        if (vadFinnsHär) {
            bild.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            int startx = me.getX() - 3;
            int starty = me.getY() - 3;
            pos.clear();

            bild.removeMouseListener(this);
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {

                    Position temx = new Position(startx + i, starty + j);
                    pos.add(temx);

                }
            }

            for (Map.Entry<Position, Platser> entrySet : finnsHär.entrySet()) {
                Position key = entrySet.getKey();
                Platser value = entrySet.getValue();
                for (Position po : pos) {
                    if ((key.x == po.x) && key.y == po.y) {
                        value.getTriangel().setVisible(true);
                    }

                }

            }

            vadFinnsHär = false;
        }

    }

    @Override
    public void mousePressed(MouseEvent me
    ) {

    }

    @Override
    public void mouseReleased(MouseEvent me
    ) {

        if (comboBox.getSelectedIndex() == 1) {

            JTextField xFält = new JTextField();
            JTextField yFält = new JTextField();
            DescribedPlaces dp = new DescribedPlaces();
            Object[] fields = {"Namn:", xFält, "Beskrivning:", yFält};

            int result = JOptionPane.showConfirmDialog(null, fields,
                    "Ny plats", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            dp.setNamn(xFält.getText());
            dp.setBeskrivning(yFält.getText());
            if (result == JOptionPane.OK_OPTION) {
                if (list.getSelectedValue() != null) {
                    dp.setKategori(new Kategori((String) list
                            .getSelectedValue()));

                } else if (list.getSelectedValue() == null) {
                    dp.setKategori(new Kategori(null));
                    dp.getKategori().setFärg(Color.BLACK);
                }

                dp.setPosition(new Position(me.getX(), me.getY()));

                ArrayList<Platser> tempAp = new ArrayList<>();
                if (positioner.containsKey(dp.getNamn())) {
                    tempAp = positioner.get(dp.getNamn());
                }

                tempAp.add(dp);
                positioner.put(dp.getNamn(), tempAp);
                bild.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                comboBox.setSelectedIndex(0);

                for (Kategori kat : kategorier) {
                    if (kat.getNamn().equalsIgnoreCase(
                            dp.getKategori().getNamn())) {
                        dp.getKategori().setFärg(kat.getFärg());

                    }
                }
                någotÄndrat = true;
            } else {
                bild.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                comboBox.setSelectedIndex(0);
            }
            Triangel t = new Triangel(dp.getPosition().x, dp.getPosition().y,
                    dp.getKategori().getFärg(), dp.getBeskrivning());
            bild.add(t);
            dp.setTriangel(t);
            this.repaint();
            finnsHär.put(dp.getPosition(), dp);

        } else if (comboBox.getSelectedIndex() == 2) {

            JPanel msgPanel = new JPanel();
            JLabel namns = new JLabel("Namn: ");
            JTextField xField = new JTextField(10);
            msgPanel.add(namns);
            msgPanel.add(xField);
            NamedPlaces np = new NamedPlaces();

            int result = JOptionPane.showConfirmDialog(null, msgPanel,
                    "Ny Plats", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            np.setNamn(xField.getText());
            if (result == JOptionPane.OK_OPTION) {
                if (list.getSelectedValue() != null) {
                    np.setKategori(new Kategori((String) list
                            .getSelectedValue()));
                } else if (list.getSelectedValue() == null) {
                    np.setKategori(new Kategori(null));
                    np.getKategori().setFärg(Color.BLACK);
                }

                np.setPosition(new Position(me.getX(), me.getY()));

                ArrayList<Platser> tempAp = new ArrayList<>();
                if (positioner.containsKey(np.getNamn())) {
                    tempAp = positioner.get(np.getNamn());
                }

                tempAp.add(np);
                positioner.put(np.getNamn(), tempAp);

                bild.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                comboBox.setSelectedIndex(0);

                if (np.getKategori() == null) {

                } else {

                    for (Kategori kat : kategorier) {
                        if (kat.getNamn().equalsIgnoreCase(
                                np.getKategori().getNamn())) {
                            np.getKategori().setFärg(kat.getFärg());
                        }
                    }

                }

            } else {
                bild.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                comboBox.setSelectedIndex(0);
            }
            någotÄndrat = true;

            Triangel t = new Triangel(np.getPosition().x, np.getPosition().y,
                    np.getKategori().getFärg(), np.getNamn());

            bild.add(t);
            np.setTriangel(t);
            this.repaint();
            finnsHär.put(np.getPosition(), np);
        }

    }

    @Override
    public void mouseEntered(MouseEvent me
    ) {

    }

    @Override
    public void mouseExited(MouseEvent me
    ) {

    }

    public void showResult(String name) {
        // / HÄMTA

        for (Map.Entry<String, ArrayList<Platser>> entrySet : positioner.entrySet()) {
            String key = entrySet.getKey();
            ArrayList<Platser> platserna = entrySet.getValue();
            for (Platser platserna1 : platserna) {
                platserna1.getTriangel().setMarkerad(false);
                platserna1.getTriangel().setVisas(false);
                
            }
        }

        ArrayList<Platser> ap = positioner.get(new String(name));
        if (ap != null) {
            drawTriangels(ap);
            for (Platser ap1 : ap) {
                ap1.getTriangel().setMarkerad(true);
                ap1.getTriangel().setVisas(true);
            }
        } else {
            JOptionPane.showMessageDialog(Layout.this, "Platsen finns inte");
        }
    }

    public void drawTriangels(ArrayList<Platser> platser) {
        for (int i = 0; i < platser.size(); i++) {

            bild.add(platser.get(i).getTriangel());
            platser.get(i).getTriangel().setVisible(true);

        }
        this.repaint();
    }

    public void showAll() {
        for (Map.Entry<String, ArrayList<Platser>> entrySet : positioner
                .entrySet()) {
            ArrayList<Platser> value = entrySet.getValue();
            drawTriangels(value);
        }
    }
     public void drawDelete(ArrayList<Platser> platser) {
        for (int i = 0; i < platser.size(); i++) {

            bild.add(platser.get(i).getTriangel());
            if(platser.get(i).getTriangel().isVisas()){
                platser.get(i).getTriangel().setVisible(true);
            }
            

        }
        this.repaint();
    }

}
