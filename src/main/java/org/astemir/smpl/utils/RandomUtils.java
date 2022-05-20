package org.astemir.smpl.utils;

import java.util.Random;

public class RandomUtils {

    private static Random random = new Random();


    public static float randomFloat(float min,float max){
        return min + random.nextFloat() * (max - min);
    }

    public static boolean doWithChance(int chance){
        if (100/chance > 0){
            return random.nextInt(100/chance) == 0;
        }
        return true;
    }

    public static int randomInt(int range){return random.nextInt(range);}

    public static int randomInt(int min,int max){
        return min+random.nextInt(max);
    }

}
