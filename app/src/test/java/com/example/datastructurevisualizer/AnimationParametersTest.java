package com.example.datastructurevisualizer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimationParametersTest {


    @Test
    void unpause() {
        AnimationParameters.unpause();
    }

    @Test
    void isPaused() {
        AnimationParameters.isPaused();
    }
}