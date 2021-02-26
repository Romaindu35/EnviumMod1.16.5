package fr.envium.enviummod.core.server.enums;

public enum  Levels {


    LVL1(0,50),
    LVL2(50,100),
    LVL3(100,150);

    int min;
    int max;

    Levels(int min, int max) {
        this.min = min;
        this.max = max;
    }
}
