import java.util.*;

public class Sheep {

    public static void main(String[] param) {
        // for debugging
        Animal[] animals = new Sheep.Animal [10];
        int rCount = 0;
        for (int i=0; i < animals.length; i++) {
            if (Math.random() < 0.5) {
                animals[i] = Sheep.Animal.goat;
                rCount++;
            } else {
                animals[i] = Sheep.Animal.sheep;
            }
        }
        System.out.println(Arrays.toString(animals));
        reorder(animals);
        System.out.println(Arrays.toString(animals));
    }

    public static void reorder(Animal[] animals) {



        Animal[] temp = new Animal[animals.length];
        int i = 0; // kitsede algindeks ajutises massiivis
        int j = animals.length - 1; // lammaste algindeks ajutises massiivis
        for (int k = 0; k < animals.length; k++) {
            switch (animals[k]) {
                case goat:
                    temp[i] = animals[k];
                    i++;
                    break;
                case sheep:
                    temp[j] = animals[k];
                    j--;
                    break;
            }
        }
        System.arraycopy(temp, 0, animals, 0, animals.length);
        if (i == animals.length) return;// piirjuhtum, kui kõik loomad on ühte sorti
        i--; // kitsede lõppindeks ajutises massiivis
        j++; // lammaste lõppindeks ajutises massiivis
        int k = 0;
        while (k <= i) {
            animals[k] = temp[k];
            k++;
        }
        k = animals.length - 1;
        while (j <= k) {
            animals[k] = temp[k];
            k--;
        }


    }

    enum Animal {goat, sheep}
}

