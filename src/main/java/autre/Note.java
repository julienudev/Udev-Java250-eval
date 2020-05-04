package autre;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Etudiant {

    private List<Note> notes;

    public double moyenne() {
        int somme = 0;

        for (Note note : this.notes) {
            somme = somme + note.getValeur();
        }

        for (int i = 0; i < this.notes.size(); i++) {
            somme = somme + this.notes.get(i).getValeur();
        }

        Stream<Note> stream = this.notes.stream();
        Stream<Integer> integerStream = stream.map(n -> n.getValeur());
        List<Integer> valeurs = integerStream.collect(Collectors.toList());

        this.notes.stream()
                .map(n -> n.getValeur())
                // .map(v -> v + 1)
                .filter(v -> v < 10)
                .findFirst();


        return somme / this.notes.size();
    }

    public void exPrimitifClass() {
        int primitif; //=0
        Integer nonPrimitif; //=null

       // nonPrimitif.toString();
    }
}

class Note {
    private String apprecation;

    private int valeur;

    private int noteMax;

    public static double moyenne(List<Note> notes) {
        return 0;//TODO
    }

    public static boolean estMeilleureQue(Note n1, Note n2) {
        return false;
    }

    public boolean estMeilleureQue(Note n2) {
        return false;
    }


    public int getValeur() {
        return valeur;
    }

    public static void main() {
        String s = "125";
    }
}
