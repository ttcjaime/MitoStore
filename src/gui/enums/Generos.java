package gui.enums;

public enum Generos {

    ELECTRONICA("Electronica"),
    HIPHOP("Hip Hop"),
    REGUETON("Regueton"),
    POP("Pop"),
    KPOP("Kpop"),
    ROCK("Rock"),
    JAZZ("Jazz");

    private String valor;

    Generos(String valor) {this.valor = valor;}

    public String getValor() {return valor;}

}
