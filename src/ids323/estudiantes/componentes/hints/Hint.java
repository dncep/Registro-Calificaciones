package ids323.estudiantes.componentes.hints;

import ids323.estudiantes.componentes.Confirmacion;
import ids323.estudiantes.componentes.Relleno;
import ids323.estudiantes.util.Constante;
import ids323.estudiantes.util.Desechable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Hint extends JDialog implements MouseListener, Desechable {
    private static final int MARGEN = 10;
    private static final int DISTANCIA = 5;
    private static final int ALTURA_FLECHA = 10;
    private static final int ANCHO_FLECHA = (int) (2*Math.ceil(Math.tan(Math.toRadians(30))* ALTURA_FLECHA));

    public static final Constante ARRIBA = new Constante("ARRIBA");
    public static final Constante ABAJO = new Constante("ABAJO");
    public static final Constante IZQUIERDA = new Constante("IZQUIERDA");
    public static final Constante DERECHA = new Constante("DERECHA");

    private Confirmacion verificadorVisibilidad = null;

    boolean desechado = false;

    int tiempoEntrada = 20;
    int tiempoSalida = 60;

    int tiempo = 0;

    private int x,y;
    private Component contenido;
    private boolean apuntado = false;
    private int desplazamientoFlecha = 0;

    private Constante posicionPreferida = ABAJO;
    private Constante posicionReal = ABAJO;
    private boolean interactivo = false;

    private Color fondo = new Color(60, 63, 65);
    private Color borde = new Color(91, 93, 95);

    private MouseAdapter adaptadorContenido;

    public Hint(JFrame dueno) {
        super(dueno);
        this.setUndecorated(true);
        this.setBackground(new Color(0,0,0,0));
        this.setContentPane(new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {

                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g.setColor(borde);
                g.fillRect(MARGEN -1, MARGEN -1, this.getWidth()-2* MARGEN +2, this.getHeight()-2* MARGEN +2);
                g.setColor(fondo);
                g.fillRect(MARGEN, MARGEN, this.getWidth()-2* MARGEN, this.getHeight()-2* MARGEN);

                if(posicionReal == ARRIBA) {
                    int[] xPoints = new int[] {this.getWidth()/2- ANCHO_FLECHA /2+ desplazamientoFlecha, this.getWidth()/2+ desplazamientoFlecha, this.getWidth()/2+ ANCHO_FLECHA /2+ desplazamientoFlecha};
                    int[] yPoints = new int[]{this.getHeight()- MARGEN, this.getHeight(), this.getHeight()- MARGEN};
                    g.setColor(borde);
                    g.fillPolygon(xPoints, yPoints,3);
                    yPoints[0]--;
                    yPoints[1] -= 2;
                    yPoints[2]--;
                    g.setColor(fondo);
                    g.fillPolygon(xPoints, yPoints,3);
                } else if(posicionReal == ABAJO) {
                    int[] xPoints = new int[] {this.getWidth()/2- ANCHO_FLECHA /2+ desplazamientoFlecha, this.getWidth()/2+ desplazamientoFlecha, this.getWidth()/2+ ANCHO_FLECHA /2+ desplazamientoFlecha};
                    int[] yPoints = new int[]{MARGEN, 0, MARGEN};
                    g.setColor(borde);
                    g.fillPolygon(xPoints, yPoints,3);
                    yPoints[0]++;
                    yPoints[1] += 2;
                    yPoints[2]++;
                    g.setColor(fondo);
                    g.fillPolygon(xPoints, yPoints,3);
                } else if(posicionReal == IZQUIERDA) {
                    int[] xPoints = new int[] {this.getWidth()- MARGEN, this.getWidth(), this.getWidth()- MARGEN};
                    int[] yPoints = new int[]{this.getHeight()/2- ANCHO_FLECHA /2+ desplazamientoFlecha, this.getHeight()/2+ desplazamientoFlecha, this.getHeight()/2+ ANCHO_FLECHA /2+ desplazamientoFlecha};
                    g.setColor(borde);
                    g.fillPolygon(xPoints, yPoints,3);
                    xPoints[0]--;
                    xPoints[1] -= 2;
                    xPoints[2]--;
                    g.setColor(fondo);
                    g.fillPolygon(xPoints, yPoints,3);
                } else if(posicionReal == DERECHA) {
                    int[] xPoints = new int[] {MARGEN, 0, MARGEN};
                    int[] yPoints = new int[]{this.getHeight()/2- ANCHO_FLECHA /2+ desplazamientoFlecha, this.getHeight()/2+ desplazamientoFlecha, this.getHeight()/2+ ANCHO_FLECHA /2+ desplazamientoFlecha};
                    g.setColor(borde);
                    g.fillPolygon(xPoints, yPoints,3);
                    xPoints[0]++;
                    xPoints[1] += 2;
                    xPoints[2]++;
                    g.setColor(fondo);
                    g.fillPolygon(xPoints, yPoints,3);
                }

            }
        });
        this.getContentPane().add(new Relleno(MARGEN),BorderLayout.NORTH);
        this.getContentPane().add(new Relleno(MARGEN),BorderLayout.SOUTH);
        this.getContentPane().add(new Relleno(MARGEN),BorderLayout.WEST);
        this.getContentPane().add(new Relleno(MARGEN),BorderLayout.EAST);

        this.addMouseListener(this);
    }

    public Hint(JFrame dueno, Component contenido) {
        this(dueno);
        setContenido(contenido);
    }

    public void actualizar() {
        this.revalidate();
        SwingUtilities.invokeLater(this::pack);
        this.actualizarPosicion();
        this.repaint();
    }

    private void actualizarPosicion() {

        int w = this.getWidth()-2* MARGEN;
        int h = this.getHeight()-2* MARGEN;

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centro = ge.getCenterPoint();
        centro.x *= 2;
        centro.y *= 2;

        Constante current = posicionPreferida;

        Rectangle proposedRect = new Rectangle(0,0,w,h);
        if(current == ARRIBA) {
            proposedRect.x = this.x - w/2;
            proposedRect.y = this.y - DISTANCIA - MARGEN - h;
        } else if(current == ABAJO) {
            proposedRect.x = this.x - w/2;
            proposedRect.y = this.y + DISTANCIA + MARGEN;
        } else if(current == IZQUIERDA) {
            proposedRect.x = this.x - DISTANCIA - MARGEN - w;
            proposedRect.y = this.y - h/2;
        } else if(current == DERECHA) {
            proposedRect.x = this.x + DISTANCIA + MARGEN;
            proposedRect.y = this.y - h/2;
        }

        boolean xFlipped = false;
        boolean yFlipped = false;

        if(current == ARRIBA || current == ABAJO) {
            if(proposedRect.y < 0 || proposedRect.y+proposedRect.height >= centro.y) {
                current = (current == ARRIBA) ? ABAJO : ARRIBA;
                yFlipped = true;
            }
            if(proposedRect.x < 0) {
                desplazamientoFlecha = proposedRect.x;
                proposedRect.x = 0;
            } else if(proposedRect.x+proposedRect.width >= centro.x) {
                desplazamientoFlecha = proposedRect.x+proposedRect.width - centro.x;
                proposedRect.x = centro.x - proposedRect.width;
            } else desplazamientoFlecha = 0;
        } else if(current == IZQUIERDA || current == DERECHA) {
            if(proposedRect.x < 0 || proposedRect.x+proposedRect.width >= centro.x) {
                current = (current == IZQUIERDA) ? DERECHA : IZQUIERDA;
                xFlipped = true;
            }
            if(proposedRect.y < 0) {
                desplazamientoFlecha = proposedRect.y;
                proposedRect.y = 0;
            } else if(proposedRect.y+proposedRect.height >= centro.y) {
                desplazamientoFlecha = proposedRect.y+proposedRect.height - centro.y;
                proposedRect.y = centro.y - proposedRect.height;
            } else desplazamientoFlecha = 0;
        }

        posicionReal = current;

        if(xFlipped) {
            if(current == IZQUIERDA) {
                proposedRect.x = this.x - DISTANCIA - MARGEN - w;
            } else {
                proposedRect.x = this.x + DISTANCIA + MARGEN;
            }
        }
        if(yFlipped) {
            if(current == ARRIBA) {
                proposedRect.y = this.y - DISTANCIA - MARGEN - h;
            } else {
                proposedRect.y = this.y + DISTANCIA + MARGEN;
            }
        }

        this.setLocation(proposedRect.x - MARGEN, proposedRect.y - MARGEN);
    }

    public void mostrarFinal() {
        this.actualizar();
        this.setFocusableWindowState(false);
        this.setVisible(true);
        this.setFocusableWindowState(interactivo);
    }

    public void mostrar(Point loc, Confirmacion visibilityCheck) {
        this.x = loc.x;
        this.y = loc.y;
        this.verificadorVisibilidad = visibilityCheck;
        if(tiempo >= 0) esconder();
        tiempo = -tiempoEntrada;

    }

    public void actualizarPosicion(Point loc) {
        this.x = loc.x;
        this.y = loc.y;
        actualizarPosicion();
    }

    public void actualizarCondicion(Confirmacion verificadorVisibilidad) {
        this.verificadorVisibilidad = verificadorVisibilidad;
    }

    public boolean isInteractivo() {
        return interactivo;
    }

    public void setInteractivo(boolean interactivo) {
        this.interactivo = interactivo;
    }

    public void esconder() {
        this.setVisible(false);
        this.apuntado = false;
        this.tiempo = 0;
    }

    public void setContenido(Component contenido) {
        if(this.contenido != null) {
            this.contenido.removeMouseListener(adaptadorContenido);
        }
        this.contenido = contenido;
        this.getContentPane().add(contenido, BorderLayout.CENTER);
        this.contenido.addMouseListener(adaptadorContenido = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                Hint.this.apuntado = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                Hint.this.apuntado = false;
            }
        });
        this.revalidate();
    }

    public Constante getPosicionPreferida() {
        return posicionPreferida;
    }

    public void setPosicionPreferida(Constante posicionPreferida) {
        this.posicionPreferida = posicionPreferida;
    }

    public int getTiempoEntrada() {
        return tiempoEntrada;
    }

    public void setTiempoEntrada(int tiempoEntrada) {
        if(tiempoEntrada <= 0) throw new IllegalArgumentException("Hint in-delay must be positive.");
        this.tiempoEntrada = tiempoEntrada;
    }

    public int getTiempoSalida() {
        return tiempoSalida;
    }

    public void setTiempoSalida(int tiempoSalida) {
        if(tiempoSalida <= 0) throw new IllegalArgumentException("Hint out-delay must be positive.");
        this.tiempoSalida = tiempoSalida;
    }

    public boolean debeContinuarMostrando() {
        return apuntado || (this.verificadorVisibilidad != null && this.verificadorVisibilidad.confirm());
    }

    public Color getColorFondo() {
        return fondo;
    }

    public Color getColorBorde() {
        return borde;
    }

    public void setColorFondo(Color background) {
        this.fondo = background;
    }

    public void setColorBorde(Color border) {
        this.borde = border;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.apuntado = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.apuntado = false;
    }

    double getDistanciaAlPunto(Point p) {
        return Math.sqrt(Math.pow(Math.max(this.getX()+ MARGEN,Math.min(this.getX()+this.getWidth()- MARGEN,p.x))-p.x,2)+Math.pow(this.getY()+this.getHeight()/2-p.y,2));
    }

    @Override
    public void dispose() {
        this.esconder();
        this.verificadorVisibilidad = null;
        this.desechado = true;
        super.dispose();
    }
}
