package encriptacion.cipher;

public class Vigenere {

    // Alfabeto extendido: mayúsculas, minúsculas, Ñ, vocales acentuadas, dígitos y símbolos
    public static final String ALFABETO =
        "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ,. /*-':{@|?}~¿¡!;_+<>%&$#()[=]" +
        "abcdefghijklmnñopqrstuvwxyz0123456789áéíóú";

    public static String cifrar(String mensaje, String clave) {
        if (clave == null || clave.isEmpty()) return mensaje;
        StringBuilder sb = new StringBuilder();
        int claveIdx = 0;
        for (int i = 0; i < mensaje.length(); i++) {
            char c = mensaje.charAt(i);
            int posMsg = ALFABETO.indexOf(c);
            if (posMsg < 0) { sb.append(c); continue; }
            int posClave = ALFABETO.indexOf(clave.charAt(claveIdx % clave.length()));
            if (posClave < 0) posClave = 0;
            int cifrado = (posMsg + posClave) % ALFABETO.length();
            sb.append(ALFABETO.charAt(cifrado));
            claveIdx++;
        }
        return sb.toString();
    }

    public static String descifrar(String mensaje, String clave) {
        if (clave == null || clave.isEmpty()) return mensaje;
        StringBuilder sb = new StringBuilder();
        int claveIdx = 0;
        for (int i = 0; i < mensaje.length(); i++) {
            char c = mensaje.charAt(i);
            int posMsg = ALFABETO.indexOf(c);
            if (posMsg < 0) { sb.append(c); continue; }
            int posClave = ALFABETO.indexOf(clave.charAt(claveIdx % clave.length()));
            if (posClave < 0) posClave = 0;
            int descifrado = ((posMsg - posClave) % ALFABETO.length() + ALFABETO.length()) % ALFABETO.length();
            sb.append(ALFABETO.charAt(descifrado));
            claveIdx++;
        }
        return sb.toString();
    }
}
