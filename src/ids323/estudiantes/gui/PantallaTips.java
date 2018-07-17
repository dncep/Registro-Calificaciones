package ids323.estudiantes.gui;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Timer;

public class PantallaTips extends JPanel {
    private Timer cronometro;
    private int milisegundos = 0;
    private int segundos = 0;

    private static final int FRECUENCIA_AVANCE = 50;
    private static final int PERIODO_TIP = 15;

    private Random random = new Random();
    private ArrayList<String> listaTips = new ArrayList<>();
    private int indiceActual = -1;
    private String tipActual = "";

    private TimerTask task;

    public PantallaTips() {
        cronometro = new Timer();
        this.setPreferredSize(new Dimension(800, 100));
    }

    public void activar() {
        activar(0);
    }

    public void activar(int delay) {
        if(indiceActual < 0) {
            mostrarSiguiente();
        }
        task = new TimerTask() {
            @Override
            public void run() {
                avanzar();
            }
        };
        cronometro.scheduleAtFixedRate(task, delay, 1000/ FRECUENCIA_AVANCE);
    }

    public void pausar() {
        if(task != null) {
            task.cancel();
            cronometro.purge();
        }
    }

    private void avanzar() {
        milisegundos += 1000/ FRECUENCIA_AVANCE;
        if(milisegundos >= 1000) {
            milisegundos = 0;
            if(++segundos >= PERIODO_TIP) {
                this.mostrarSiguiente();
                segundos = 0;
            }
        }
        this.repaint();
    }

    private void mostrarSiguiente() {
        if(listaTips.isEmpty()) return;
        if(++indiceActual >= listaTips.size()) {
            shuffle();
            indiceActual = 0;
        }
        tipActual = listaTips.get(indiceActual);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = ((Graphics2D) g);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if(segundos < 1 || segundos >= PERIODO_TIP -1) {
            float transitionTime = milisegundos;
            if(segundos < 1) transitionTime += 1000;
            transitionTime /= 2000;
            transitionTime *= 2 * Math.PI;
            transitionTime -= Math.PI;

            float opacity = (float) (1-((Math.cos(transitionTime)+1)/2));

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        }

        g.setColor(this.getForeground());
        g.setFont(this.getFont());
        FontMetrics metrics = g.getFontMetrics();
        g.drawString(tipActual, (this.getWidth()-metrics.stringWidth(tipActual))/2, (this.getHeight()-metrics.getHeight())/2);

        g.dispose();
    }

    public void setTips(Collection<String> tips) {
        listaTips.clear();
        listaTips.addAll(tips);
        tipActual = "";
        indiceActual = -1;
        shuffle();
    }

    private void shuffle() {
        for(int i = 0; i < listaTips.size(); i++) {
            int index = i+random.nextInt(listaTips.size()-i-((i == 0) ? 1 : 0));
            String tip = listaTips.get(index);
            listaTips.remove(index);
            listaTips.add(i, tip);
        }
    }
}
