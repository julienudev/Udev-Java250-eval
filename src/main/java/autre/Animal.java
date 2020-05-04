package autre;

import java.util.ArrayList;
import java.util.List;

public interface Animal {
    void crier();
}

class Chien implements Animal {
    public void crier() {
        System.out.println("Ouaff!");
    }
}

class Chat implements Animal {
    public void crier() {
        System.out.println("Miaou!");
    }
}

class Main {
    public static void main() {
        List<Animal> animaux = new ArrayList<>();
        animaux.add(new Chien());
        animaux.add(new Chien());
        animaux.add(new Chat());
        animaux.add(new Chien());

        //..

        for (Animal animal : animaux) {
//            if (animal instanceof Chat) {
//                ((Chat) animal).crier();
//            }
//            if (animal instanceof Chien) {
//                ((Chien) animal).crier();
//            }
            animal.crier();
        }

    }
}