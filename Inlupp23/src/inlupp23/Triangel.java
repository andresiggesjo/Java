package inlupp23;

import java.awt.*;
import java.awt.event.MouseListener;
import java.io.StringWriter;
import javax.swing.*;
import org.w3c.dom.events.MouseEvent;

public class Triangel extends JComponent implements MouseListener {

    private boolean markerad = false;
    private boolean klickad = false;
    private boolean visas = false;

    private int x, y;
    private String beskrivning;
    private Color c;
    private JTextArea beskrivningsFält = new JTextArea(50, 100);

    public Triangel(int x, int y, Color c, String beskrivining) {
        setBounds(x - 10, y - 20, 21, 21);

        Dimension d = new Dimension(50, 50);
        setPreferredSize(d);
        setMaximumSize(d);
        setMinimumSize(d);
        this.x = x;
        this.y = y;
        this.c = c;
        this.beskrivning = beskrivining;
        this.addMouseListener(this);
        visas = true;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Polygon poly = new Polygon(new int[]{0, 10, 20}, new int[]{0, 20,
            0}, 3);

        if (!klickad) {
            setBounds(x - 10, y - 20, 21, 21);
            g.setColor(c);
            g.fillPolygon(poly);
            g.drawPolygon(poly);

        }

        if (markerad) {

            g.drawRect(0, 0, 20, 20);

        }

        if (klickad) {
            setBounds(x + 10, y - 40, 50, 100);
            beskrivningsFält.setSize(50, 100);
            beskrivningsFält.setBackground(Color.YELLOW);
            beskrivningsFält.setText(beskrivning);
            beskrivningsFält.setEditable(false);
            beskrivningsFält.setLineWrap(true);
            beskrivningsFält.setWrapStyleWord(true);
            beskrivningsFält.addMouseListener(this);

            add(beskrivningsFält);

//			setBounds(x + 10, y - 40, 100, 35);
//			int fontSize = 15;
//
//			JLabel texten = new JLabel(beskrivning);
//			texten.getBounds();
//
//			g.setColor(Color.YELLOW);
//			g.fillRect(0, 0, 100, 35);
//			g.setColor(Color.BLACK);
//			g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
//			g.drawString(beskrivning, 1, 10);
        }

    }

    public void setVisibility(boolean v) {
        this.setVisible(v);

    }

    public void setMarkerad(boolean b) {

        markerad = b;
        repaint();
    }

    public void setKlickad(boolean b) {
        klickad = b;
        repaint();

    }

    public boolean isMarkerad() {
        return markerad;
    }

    public boolean isVisas() {
        return visas;
    }

    public void setVisas(boolean visas) {
        this.visas = visas;
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {

        beskrivningsFält.removeMouseListener(this);
        remove(beskrivningsFält);

        if (SwingUtilities.isLeftMouseButton(e)) {

            if (markerad) {
                setMarkerad(false);

                return;

            }
            if (!markerad && !klickad) {
                setMarkerad(true);
                return;
            }
        }

        if (SwingUtilities.isRightMouseButton(e)) {

            if (klickad) {
                setKlickad(false);
                return;

            }
            if (!klickad) {

                setMarkerad(false);
                setKlickad(true);

                return;

            }

        }
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub

    }

}
