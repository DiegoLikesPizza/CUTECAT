package com.CUTECAT;

import static com.CUTECAT.diegoutil.DiegoMathUtils.*;
import static com.CUTECAT.diegoutil.DiegoPhysicsUtils.*;
import static com.CUTECAT.diegoutil.DiegoStringUtils.*;

public class Main {

    public static void main(String[] args) {

        print(ToString(getGravitationalPull((double)getRandom(1, 25))));
    }
}