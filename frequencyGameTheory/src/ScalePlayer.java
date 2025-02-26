/*
 * Player class for the Scale Following player
 */
public class ScalePlayer extends Player {
    public ScalePlayer() {
        super();
    }

    @Override
    public double genNote() {
        //Bb mixolydian scale includes all octaves of Bb, C, D, Eb, F, G, Ab
        double[] Bb0 = new double[]{28.31, 29.99};
        double[] Bb1 = new double[]{56.61, 60.00};
        double[] Bb2 = new double[]{113.23, 120.00};
        double[] Bb3 = new double[]{226.46, 240.00};
        double[] Bb4 = new double[]{452.92, 480.00};
        double[] Bb5 = new double[]{905.84, 960.00};
        double[] Bb6 = new double[]{1811.68, 1920.00};
        double[] Bb7 = new double[]{3623.36, 3840.00};

        double[] C1 = new double[]{31.77, 33.66};
        double[] C2 = new double[]{63.55, 67.32};
        double[] C3 = new double[]{127.10, 134.64};
        double[] C4 = new double[]{254.20, 269.29};
        double[] C5 = new double[]{508.40, 538.58};
        double[] C6 = new double[]{1016.80, 1077.16};
        double[] C7 = new double[]{2033.60, 2154.32};
        double[] C8 = new double[]{4067.20, 4186};

        double[] D1 = new double[]{35.67, 37.78};
        double[] D2 = new double[]{71.34, 75.57};
        double[] D3 = new double[]{142.68, 151.14};
        double[] D4 = new double[]{285.36, 302.28};
        double[] D5 = new double[]{570.72, 604.56};
        double[] D6 = new double[]{1141.44, 1209.12};
        double[] D7 = new double[]{2282.88, 2418.24};
        
        double[] Eb1 = new double[]{37.78, 40.03};
        double[] Eb2 = new double[]{75.57, 80.07};
        double[] Eb3 = new double[]{151.14, 160.14};
        double[] Eb4 = new double[]{302.28, 320.29};
        double[] Eb5 = new double[]{604.56, 640.57};
        double[] Eb6 = new double[]{1209.12, 1281.14};
        double[] Eb7 = new double[]{2418.24, 2562.28};
        
        double[] F1 = new double[]{42.41, 44.93};
        double[] F2 = new double[]{84.83, 89.87};
        double[] F3 = new double[]{169.65, 179.74};
        double[] F4 = new double[]{339.30, 359.48};
        double[] F5 = new double[]{678.60, 718.96};
        double[] F6 = new double[]{1357.20, 1437.92};
        double[] F7 = new double[]{2714.40, 2875.84};
        
        double[] G1 = new double[]{47.61, 50.43};
        double[] G2 = new double[]{95.22, 100.87};
        double[] G3 = new double[]{190.44, 201.74};
        double[] G4 = new double[]{380.89, 403.49};
        double[] G5 = new double[]{761.77, 806.97};
        double[] G6 = new double[]{1523.55, 1613.94};
        double[] G7 = new double[]{3047.09, 3227.88};
        
        double[] Ab1 = new double[]{50.43, 53.43};
        double[] Ab2 = new double[]{100.87, 106.87};
        double[] Ab3 = new double[]{201.74, 213.74};
        double[] Ab4 = new double[]{403.49, 427.48};
        double[] Ab5 = new double[]{806.97, 854.96};
        double[] Ab6 = new double[]{1613.94, 1709.92};
        double[] Ab7 = new double[]{3227.88, 3419.84};

        double[][] allPossibleNotes = {Bb0, Bb1, Bb2, Bb3, Bb4, Bb5, Bb6, Bb7, C1, C2, C3, C4, C5, C6, C7, C8, D1, D2, D3, D4, D5, D6, D7, Eb1, Eb2, Eb3, Eb4, Eb5, Eb6, Eb7, F1, F2, F3, F4, F5, F6, F7, G1, G2, G3, G4, G5, G6, G7, Ab1, Ab2, Ab3, Ab4, Ab5, Ab6, Ab7};
        //picks a random octave of a possible note in the scale
        int randNoteOctave = ((int) (Math.random() * allPossibleNotes.length));
        //picks a random integer value to player within this octave range
        return ((Math.random() * (allPossibleNotes[randNoteOctave][1]-allPossibleNotes[randNoteOctave][0]+1) + allPossibleNotes[randNoteOctave][0]));
    }
}