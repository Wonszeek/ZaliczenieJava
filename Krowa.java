import java.io.Serializable;

class Krowa implements Serializable {
    private static final long serialVersionUID = 18L;
    private String imie;

    public Krowa(String imie) {
        this.imie = imie;
    }

    public String getImie() {
        return imie;
    }

    @Override
    public String toString() {
        return "Krowa{" + "Imie=" + imie + '}';
    }
}
