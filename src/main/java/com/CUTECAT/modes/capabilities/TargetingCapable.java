// TargetingCapable.java
package com.CUTECAT.modes.capabilities;

public interface TargetingCapable {
    void aim(int distance, int angle) throws Exception;
    boolean isTargetLocked();
}