package com.example.smarthome.data.model;

/**
 * Defines the category of a smart home device, determining its available controls and safety behaviors.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2 = {"Lcom/example/smarthome/data/model/DeviceType;", "", "(Ljava/lang/String;I)V", "OUTLET", "MULTI_SWITCH", "IRON_SLOT", "LIGHT_SCHEDULE", "CAMERA", "app_debug"})
public enum DeviceType {
    /*public static final*/ OUTLET /* = new OUTLET() */,
    /*public static final*/ MULTI_SWITCH /* = new MULTI_SWITCH() */,
    /*public static final*/ IRON_SLOT /* = new IRON_SLOT() */,
    /*public static final*/ LIGHT_SCHEDULE /* = new LIGHT_SCHEDULE() */,
    /*public static final*/ CAMERA /* = new CAMERA() */;
    
    DeviceType() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.example.smarthome.data.model.DeviceType> getEntries() {
        return null;
    }
}