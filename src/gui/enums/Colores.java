package gui.enums;

public enum Colores {
    NEGRO("Negro"),
    AMARILLO("Amarillo"),
    ROJO("Rojo"),
    BLANCO("Blanco"),
    AZUL("Azul"),
    VERDE("Verde"),
    MORADO("Morado"),
    INDIGO("Indigo"),
    ROSADO("Rosado"),
    CAFE("Cafe"),
    NARANJA("Naranja");

    private String valor;

    Colores(String valor) {this.valor = valor;}

    public String getValor() {return valor;}

}
