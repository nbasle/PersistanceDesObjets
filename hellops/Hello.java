
import java.io.Serializable;

public class Hello implements Serializable {

    private String valeur;
    private Petstore petstore;

    // Constructeur
    public Hello(String valeur) {
        this.valeur = valeur;
    }

    public void setPetstore(Petstore petstore) {
        this.petstore = petstore;
    }

    // Cette méhode retourne le contenu de la classe Hello
    // concatené avec le contenu de la classe Petstore
    public String toString() {
        return valeur + petstore;
    }
}
