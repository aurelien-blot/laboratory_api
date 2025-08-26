package com.castruche.laboratory_api.main_api.dto.util.noise;

import java.util.Random;

public class PerlinNoise {

    private final int[] p; // Duplicated permutation array

    // Constructeur avec une graine
    public PerlinNoise(long seed) {
        int[] permutation = new int[256];
        p = new int[512];
        Random random = new Random(seed);

        // Initialiser permutation avec des valeurs de 0 à 255
        for (int i = 0; i < 256; i++) {
            permutation[i] = i;
        }

        // Mélanger l'array avec un algorithme de mélange
        for (int i = 255; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = permutation[i];
            permutation[i] = permutation[index];
            permutation[index] = temp;
        }

        // Dupliquer le tableau de permutation pour éviter le débordement
        for (int i = 0; i < 512; i++) {
            p[i] = permutation[i % 256];
        }
    }

    // Générer du bruit de Perlin
    public double noise(double x, double y) {
        int X = (int) Math.floor(x) & 255; // Indice du coin
        int Y = (int) Math.floor(y) & 255;
        x -= Math.floor(x); // Fractionnaire de x
        y -= Math.floor(y);
        double u = fade(x); // Appliquer la courbe de fade à x
        double v = fade(y); // Appliquer la courbe de fade à y
        int aa, ab, ba, bb;
        aa = p[p[X] + Y];
        ab = p[p[X] + Y + 1];
        ba = p[p[X + 1] + Y];
        bb = p[p[X + 1] + Y + 1];

        // Interpoler les résultats
        return lerp(v, lerp(u, grad(aa, x, y), grad(ba, x - 1, y)),
                lerp(u, grad(ab, x, y - 1), grad(bb, x - 1, y - 1)));
    }

    // Courbe de fade (lissage des valeurs)
    private double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    // Fonction de linéarisation (interpolation linéaire)
    private double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    // Fonction gradiente (calcul du gradient en fonction de l'indice)
    private double grad(int hash, double x, double y) {
        int h = hash & 3;
        double u = h < 2 ? x : y;
        double v = h < 2 ? y : x;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }
}
