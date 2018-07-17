package ids323.estudiantes.data;

/**
 * Clase para métodos de utilidad.
 * */
public class Util {
    /**
     * Cadena de longitud <code>2n</code> donde cada caracter en posición <code>i &lt; n</code> tiene su representación
     * sin acentos ni tildes en la posición <code>n + i</code>.
     * */
    private static final String MAPA_TILDES = "áéíóúñÁÉÍÓÚÑaeiounAEIOUN";

    /**
     * Elimina los acentos y tildes de la cadena dada.
     * <br><br>
     * Los siguientes caracteres:
     * <br>
     * <code>áéíóúñÁÉÍÓÚÑ</code>
     * <br>
     * son reemplazados por:
     * <br>
     * <code>aeiounAEIOUN</code>
     * <br>
     * respectivamente
     *
     * @param str La cadena a normalizar.
     * @return La cadena sin acentos ni tildes.
     */
    public static String normalizar(String str) {
        String con = MAPA_TILDES.substring(0, MAPA_TILDES.length()/2);
        String sin = MAPA_TILDES.substring(MAPA_TILDES.length()/2);

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            int indice = con.indexOf(c);
            if(indice >= 0) {
                c = sin.charAt(indice);
            }
            sb.append(c);
        }

        return sb.toString();
    }

    /**
     * Retorna el módulo matemático de dos números. Con parámetros a y b, el operador <code>%</code> retorna un número
     * entre -b y b exclusivos. Este método retorna un número entre 0 inclusivo y b exclusivo, siguiendo la definición
     * matemática de <code>mod</code>.
     * */
    public static int mod(int a, int b) {
        int res = a % b;
        if(res < 0) res += b;
        return res;
    }

    /**
     * La clase util no debe ser instanciada.
     * */
    private Util() {}
}
