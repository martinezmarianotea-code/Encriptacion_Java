# Cifrado Vigenère

**Español** · [English](#english)

Aplicación de escritorio en Java Swing para cifrar y descifrar texto con el cifrado Vigenère. Usa un alfabeto extendido de 100 caracteres: mayúsculas, minúsculas, Ñ, vocales acentuadas, dígitos y signos de puntuación.

## Funciones

- Modo cifrar y descifrar con un solo botón.
- Alfabeto extendido compatible con texto en español.
- Los caracteres fuera del alfabeto se conservan sin cambios.
- Copiar el resultado al portapapeles.
- Interfaz moderna sin dependencias externas.

## Requisitos

- JDK 8 o superior.

## Uso

```bash
git clone https://github.com/martinezmarianotea-code/Encriptacion_Java.git
cd Encriptacion_Java
./run.sh
```

Sin `run.sh` (por ejemplo, en Windows con Git Bash):

```bash
javac -encoding UTF-8 -d bin $(find src -name "*.java")
java -cp bin encriptacion.Main
```

## Estructura

```text
src/encriptacion/
├── Main.java              Punto de entrada
├── cipher/Vigenere.java   Lógica de cifrado y descifrado
└── views/
    ├── MainForm.java      Ventana principal
    └── AppTheme.java      Colores, fuentes y estilos
```

> Proyecto educativo. Vigenère es un cifrado clásico y no es seguro para proteger información real.

---

## English

Java Swing desktop app to encrypt and decrypt text with the Vigenère cipher. It uses an extended 100-character alphabet: uppercase, lowercase, Ñ, accented vowels, digits and punctuation.

### Features

- Encrypt and decrypt modes with a single toggle.
- Extended alphabet that supports Spanish text.
- Characters outside the alphabet are kept as they are.
- Copy the result to the clipboard.
- Modern UI with no external dependencies.

### Requirements

- JDK 8 or later.

### Usage

```bash
git clone https://github.com/martinezmarianotea-code/Encriptacion_Java.git
cd Encriptacion_Java
./run.sh
```

> Educational project. Vigenère is a classical cipher and is not secure for protecting real data.

## License

[MIT](LICENSE)
