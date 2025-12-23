package gui.enums;

public enum Discograficas {

    UNIVERSAL("Universal Music Group"),
    SONY("Sony Music"),
    WARNER("Warner Music Group"),
    COLUMBIA("Columbia Records");

    private String valor;

    Discograficas(String valor) {this.valor = valor;}

    public String getValor() {return valor;}

}
