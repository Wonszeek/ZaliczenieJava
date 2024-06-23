import java.io.Serializable;

class Kot implements Serializable {
    private static final long serialVersionUID = 18L;
    private String imie;

    public Kot(String imie) {
        this.imie = imie;
    }

    public String getImie() {
        return imie;
    }

    @Override
    public String toString() {
        return "Kot{" + "Imie=" + imie + '}';
    }
}
