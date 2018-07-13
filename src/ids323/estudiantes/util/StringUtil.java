package ids323.estudiantes.util;

import java.util.Random;

/**
 * Things for strings.
 */
public class StringUtil {

    private static final char[] caracteresAleatorios = new char[] {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','_','-'};

    public static String elipsis(String str, int max) {
        if (str.length() > max) {
            return (str.substring(0, max - 3) + "...").intern();
        } else {
            return str;
        }
    }

    public static String repetir(String str, int amount) {
        StringBuilder o = new StringBuilder();
        for(int i = 0; i < amount; i++) {
            o.append(str);
        }
        return o.toString();
    }

    public static String getIniciales(String s) {
        StringBuilder initials = new StringBuilder();

        char lastChar = 0;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0) {
                initials.append(s.charAt(i));
            } else if(Character.isUpperCase(s.charAt(i))) {
                initials.append(s.charAt(i));
            } else if((lastChar == '_' || lastChar == '-') && Character.isAlphabetic(s.charAt(i))) {
                initials.append(s.charAt(i));
            }
            lastChar = s.charAt(i);
        }
        return initials.toString().toUpperCase();
    }

    public static char getCaracterAleatorio() {
        Random rand = new Random();

        return caracteresAleatorios[rand.nextInt(caracteresAleatorios.length)];
    }

    public static String getCadenaAleatorio(int lon) {
        String s = "";
        for (int i = 0; i < lon; i++) {
            s += getCaracterAleatorio();
        }
        return s;
    }

    public static int contarSecuencia(String str, String pattern) {
        return contarSecuencia(str, pattern, 0);
    }

    public static int contarSecuencia(String str, String pattern, int start) {
        int count = 0;

        for(int i = start; i < str.length();) {
            if(str.substring(i).startsWith(pattern)) {
                count++;
                i += pattern.length();
            } else break;
        }

        return count;
    }

    public static String omitirDecimales(double n) {
        if(n % 1 == 0) {
            return Integer.toString((int) n);
        } else {
            return Double.toString(n);
        }
    }

    public static String omitirDecimales(double n, int p) {
        double pow = Math.pow(10,p);
        return "" + ((Math.round(n*pow))/pow);
    }

    private StringUtil() {}
}
