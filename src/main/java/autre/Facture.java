package autre;

public class Facture {

    private double montant; //propriété

    private static final int tauxTva = 20; // propriété static


    public double calculer() { // méthode dynamique
        return this.getMontant();
    }

    public static double calculerStatic(Facture f) { // méthode statique
        return f.getMontant();
    }

    public double getMontant() {
        return montant;
    }
}
