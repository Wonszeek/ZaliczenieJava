import java.io.Serializable;

class Pies implements Serializable {
    private static final long serialVersionUID = 18L;
    private String imie;

    public Pies(String imie) {
        this.imie = imie;
    }

    public String getImie() {
        return imie;
    }

    @Override
    public String toString() {
        return "Pies{" + "Imie=" + imie + '}';
    }
}
