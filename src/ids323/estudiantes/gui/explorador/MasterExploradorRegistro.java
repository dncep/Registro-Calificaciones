package ids323.estudiantes.gui.explorador;

import ids323.estudiantes.Main;
import ids323.estudiantes.data.Util;
import ids323.estudiantes.gui.Colores;
import ids323.estudiantes.gui.TokenModulo;
import ids323.estudiantes.gui.Ventana;
import ids323.estudiantes.gui.explorador.base.MasterExplorador;
import ids323.estudiantes.gui.explorador.base.elementos.SeparadorExplorador;
import ids323.estudiantes.util.Comunes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by User on 5/16/2017.
 */
public class MasterExploradorRegistro extends MasterExplorador {
    private TokenModulo root;

    private ArrayList<TokenModulo> searchResults = new ArrayList<>();
    private SearchResultTokenRoot searchToken = new SearchResultTokenRoot();

    public MasterExploradorRegistro() {
        root = Main.registro.rootToken;

        colors.put("fondo", Colores.PRIMARIO_MAS_OSCURO);
        colors.put("item.fondo", Colores.PRIMARIO_MAS_OSCURO);
        colors.put("item.texto", Colores.TEXTO);
        colors.put("item.seleccionado.fondo", Colores.ENFASIS);
        colors.put("item.seleccionado.texto", Colores.TEXTO);
        colors.put("item.rollover.fondo", Colores.PRIMARIO_OSCURO);
        colors.put("item.rollover.texto", Colores.TEXTO);

        rowHeight = 25;
        indentPerLevel = 20;
        initialIndent = 5;

        selectionStyle = "LINE_LEFT";
        selectionLineThickness = 2;

        assets.put("expand", Comunes.getIcono("expand").getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        assets.put("collapse", Comunes.getIcono("collapse").getScaledInstance(16, 16, Image.SCALE_SMOOTH));

        refresh();

        KeyStroke findKeystroke = KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK);

        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(findKeystroke, "findKeystroke");

        this.getActionMap().put("findKeystroke", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                triggerSearch();
            }
        });
    }

    @Override
    public void refresh() {
        clearSelected();
        refresh(new ArrayList<>(this.getExpandedElements()));
    }

    private void refresh(ArrayList<TokenModulo> toOpen) {
        children.clear();
        flatList.clear();
        this.getExpandedElements().clear();

        Collection<TokenModulo> subTokens = root.getSubTokens();
        for(TokenModulo token : subTokens) {
            this.children.add(new ItemExploradorRegistro(this, token, toOpen));
        }

        if(!searchResults.isEmpty()) {
            this.children.add(new SeparadorExplorador(this));
            this.children.add(new ItemExploradorRegistro(this, searchToken, toOpen));
        }

        repaint();
    }

    public void triggerSearch() {
        String query = JOptionPane.showInputDialog(Ventana.jframe, "Introduzca lo que desea buscar", "Buscar", JOptionPane.QUESTION_MESSAGE);
        if(query == null) return;

        searchResults.clear();

        find(Util.normalizar(query.toLowerCase()), Main.registro.rootToken, searchResults);

        String message = "";

        if(searchResults.isEmpty()) {
            message = "No se encontraron resultados";
        } else if(searchResults.size() == 1) {
            message = "Se encontró 1 resultado";
        } else {
            message = "Se encontraron " + searchResults.size() + " resultados";
        }

        JOptionPane.showMessageDialog(Ventana.jframe, message);

        refresh();
    }

    private void find(String query, TokenModulo token, ArrayList<TokenModulo> found) {
        String searchInfo = token.getInformacionBusqueda();
        if(searchInfo != null) {
            searchInfo = Util.normalizar(searchInfo.toLowerCase());
            if(searchInfo.contains(query)) found.add(token);
        }
        for(TokenModulo subToken : token.getSubTokens()) {
            find(query, subToken, found);
        }
    }

    public ArrayList<TokenModulo> getSearchResults() {
        return searchResults;
    }

    @Override
    protected void selectionUpdated() {
        super.selectionUpdated();
    }
}
