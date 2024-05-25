package com.mygdx.fourq;

public enum FruitType {
    STRAWBERRY(20, "strawberry.png"),
    BANANA(30, "banana.png"),
    ORANGE(50, "orange.png"),
    APPLE(60, "apple.png"),
    WATERMELON(100, "watermelon.png");

    public final int value;
    public final String spritePath;

    FruitType(int value, String spritePath) {
        this.value = value;
        this.spritePath = spritePath;
    }
}
