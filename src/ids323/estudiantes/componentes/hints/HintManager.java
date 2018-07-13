package ids323.estudiantes.componentes.hints;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HintManager {
    private final JFrame dueno;
    private ArrayList<Hint> hints = new ArrayList<>();
    private Timer cronometro = new Timer();

    private static final int DISTANCIA_ESCONDER = 30;
    private static final int DISTANCIA_ESCONDER_FINAL = 200;

    public HintManager(JFrame dueno) {
        this.dueno = dueno;
        cronometro.schedule(new TimerTask() {
            @Override
            public void run() {
                for(int i = 0; i < hints.size(); i++) {
                    Hint hint = hints.get(i);
                    if(hint.desechado) {
                        hints.remove(i);
                        i--;
                        continue;
                    }
                    if(hint.tiempo < 0) {
                        hint.tiempo++;
                        if(!hint.debeContinuarMostrando()) {
                            hint.tiempo = 0;
                            continue;
                        }
                        if(hint.tiempo == 0) {
                            hint.mostrarFinal();
                        }
                    } else if(hint.tiempo == 0) {
                        if(hint.isShowing()) {
                            double distance = (hint.isInteractivo()) ? hint.getDistanciaAlPunto(MouseInfo.getPointerInfo().getLocation()) : 0;
                            if(!hint.isInteractivo() || distance >= DISTANCIA_ESCONDER) {
                                if(!hint.debeContinuarMostrando()) {
                                    hint.tiempo = hint.tiempoSalida;
                                }
                            }
                        }
                    } else {
                        double distance = hint.getDistanciaAlPunto(MouseInfo.getPointerInfo().getLocation());
                        if(distance >= DISTANCIA_ESCONDER_FINAL) {
                            hint.tiempo = 0;
                            hint.esconder();
                            continue;
                        }
                        if(hint.debeContinuarMostrando()) {
                            hint.tiempo = 0;
                            continue;
                        }
                        hint.tiempo--;
                        if(hint.tiempo == 0) {
                            hint.esconder();
                        }
                    }
                }
            }
        }, 0, 10);
    }

    public TextHint createTextHint(String text) {
        TextHint newHint = new TextHint(dueno, text);
        this.hints.add(newHint);
        return newHint;
    }
}
