import java.io.Serializable;

class Kon implements Serializable {
    private static final long serialVersionUID = 18L;
    private String imie;

    public Kon(String imie) {
        this.imie = imie;
    }

    public String getImie() {
        return imie;
    }

    @Override
    public String toString() {
        return "Kon{" + "Imie=" + imie + '}';
    }
}
