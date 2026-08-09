package com.example.smarthome.data.model;

/**
 * Represents an individual controllable smart device or sensor in the home.
 *
 * @property id Unique identifier for the device.
 * @property floorId Identifier of the floor where the device is located.
 * @property name Human-readable name of the device.
 * @property type Category of the device defined by [DeviceType].
 * @property status Current operational status defined by [DeviceStatus].
 * @property gridX X-coordinate position on the floor plan grid.
 * @property gridY Y-coordinate position on the floor plan grid.
 * @property maxOnDurationMinutes Maximum allowed ON duration before safety-cutoff triggers automatically.
 * @property turnedOnAt Epoch timestamp (ms) when the device was last turned ON, used to calculate safety cutoff elapsed time.
 * @property scheduleStart Scheduled start time (e.g., "08:00") for automated light or device activation.
 * @property scheduleEnd Scheduled end time (e.g., "22:00") for automated device deactivation.
 * @property switchCount Number of individual channels/switches for multi-switch devices.
 * @property switchStates Map of individual switch identifiers to their boolean ON/OFF states.
 * @property cameraSnapshotUrl URL pointing to the latest snapshot image for camera devices.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\u0010\u000b\n\u0002\bC\b\u0087\b\u0018\u00002\u00020\u0001B\u0007\b\u0016\u00a2\u0006\u0002\u0010\u0002B\u00ab\u0001\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\f\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0004\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0004\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\f\u0012\u0016\b\u0002\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u0015\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\u0002\u0010\u0018J\t\u0010E\u001a\u00020\u0004H\u00c6\u0003J\u000b\u0010F\u001a\u0004\u0018\u00010\u0004H\u00c6\u0003J\u000b\u0010G\u001a\u0004\u0018\u00010\u0004H\u00c6\u0003J\u0010\u0010H\u001a\u0004\u0018\u00010\fH\u00c6\u0003\u00a2\u0006\u0002\u0010(J\u0017\u0010I\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u0015H\u00c6\u0003J\u000b\u0010J\u001a\u0004\u0018\u00010\u0004H\u00c6\u0003J\t\u0010K\u001a\u00020\u0004H\u00c6\u0003J\t\u0010L\u001a\u00020\u0004H\u00c6\u0003J\t\u0010M\u001a\u00020\bH\u00c6\u0003J\t\u0010N\u001a\u00020\nH\u00c6\u0003J\t\u0010O\u001a\u00020\fH\u00c6\u0003J\t\u0010P\u001a\u00020\fH\u00c6\u0003J\u0010\u0010Q\u001a\u0004\u0018\u00010\fH\u00c6\u0003\u00a2\u0006\u0002\u0010(J\u0010\u0010R\u001a\u0004\u0018\u00010\u0010H\u00c6\u0003\u00a2\u0006\u0002\u0010=J\u00b4\u0001\u0010S\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u00042\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\f2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\f2\u0016\b\u0002\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u00152\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0004H\u00c6\u0001\u00a2\u0006\u0002\u0010TJ\u0013\u0010U\u001a\u00020\u00162\b\u0010V\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010W\u001a\u00020\fH\u00d6\u0001J\t\u0010X\u001a\u00020\u0004H\u00d6\u0001R\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001a\u0010\u0005\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001a\"\u0004\b\u001e\u0010\u001cR\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010\r\u001a\u00020\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010 \"\u0004\b$\u0010\"R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u001a\"\u0004\b&\u0010\u001cR\u001e\u0010\u000e\u001a\u0004\u0018\u00010\fX\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010+\u001a\u0004\b\'\u0010(\"\u0004\b)\u0010*R\u001a\u0010\u0006\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u001a\"\u0004\b-\u0010\u001cR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u001a\"\u0004\b/\u0010\u001cR\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b0\u0010\u001a\"\u0004\b1\u0010\u001cR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b2\u00103\"\u0004\b4\u00105R\u001e\u0010\u0013\u001a\u0004\u0018\u00010\fX\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010+\u001a\u0004\b6\u0010(\"\u0004\b7\u0010*R(\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b8\u00109\"\u0004\b:\u0010;R\u001e\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010@\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bA\u0010B\"\u0004\bC\u0010D\u00a8\u0006Y"}, d2 = {"Lcom/example/smarthome/data/model/Device;", "", "()V", "id", "", "floorId", "name", "type", "Lcom/example/smarthome/data/model/DeviceType;", "status", "Lcom/example/smarthome/data/model/DeviceStatus;", "gridX", "", "gridY", "maxOnDurationMinutes", "turnedOnAt", "", "scheduleStart", "scheduleEnd", "switchCount", "switchStates", "", "", "cameraSnapshotUrl", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/example/smarthome/data/model/DeviceType;Lcom/example/smarthome/data/model/DeviceStatus;IILjava/lang/Integer;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/util/Map;Ljava/lang/String;)V", "getCameraSnapshotUrl", "()Ljava/lang/String;", "setCameraSnapshotUrl", "(Ljava/lang/String;)V", "getFloorId", "setFloorId", "getGridX", "()I", "setGridX", "(I)V", "getGridY", "setGridY", "getId", "setId", "getMaxOnDurationMinutes", "()Ljava/lang/Integer;", "setMaxOnDurationMinutes", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "getName", "setName", "getScheduleEnd", "setScheduleEnd", "getScheduleStart", "setScheduleStart", "getStatus", "()Lcom/example/smarthome/data/model/DeviceStatus;", "setStatus", "(Lcom/example/smarthome/data/model/DeviceStatus;)V", "getSwitchCount", "setSwitchCount", "getSwitchStates", "()Ljava/util/Map;", "setSwitchStates", "(Ljava/util/Map;)V", "getTurnedOnAt", "()Ljava/lang/Long;", "setTurnedOnAt", "(Ljava/lang/Long;)V", "Ljava/lang/Long;", "getType", "()Lcom/example/smarthome/data/model/DeviceType;", "setType", "(Lcom/example/smarthome/data/model/DeviceType;)V", "component1", "component10", "component11", "component12", "component13", "component14", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/example/smarthome/data/model/DeviceType;Lcom/example/smarthome/data/model/DeviceStatus;IILjava/lang/Integer;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/util/Map;Ljava/lang/String;)Lcom/example/smarthome/data/model/Device;", "equals", "other", "hashCode", "toString", "app_debug"})
public final class Device {
    @org.jetbrains.annotations.NotNull()
    private java.lang.String id;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String floorId;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String name;
    @org.jetbrains.annotations.NotNull()
    private com.example.smarthome.data.model.DeviceType type;
    @org.jetbrains.annotations.NotNull()
    private com.example.smarthome.data.model.DeviceStatus status;
    private int gridX;
    private int gridY;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Integer maxOnDurationMinutes;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Long turnedOnAt;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String scheduleStart;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String scheduleEnd;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Integer switchCount;
    @org.jetbrains.annotations.Nullable()
    private java.util.Map<java.lang.String, java.lang.Boolean> switchStates;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String cameraSnapshotUrl;
    
    public Device(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String floorId, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    com.example.smarthome.data.model.DeviceType type, @org.jetbrains.annotations.NotNull()
    com.example.smarthome.data.model.DeviceStatus status, int gridX, int gridY, @org.jetbrains.annotations.Nullable()
    java.lang.Integer maxOnDurationMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.Long turnedOnAt, @org.jetbrains.annotations.Nullable()
    java.lang.String scheduleStart, @org.jetbrains.annotations.Nullable()
    java.lang.String scheduleEnd, @org.jetbrains.annotations.Nullable()
    java.lang.Integer switchCount, @org.jetbrains.annotations.Nullable()
    java.util.Map<java.lang.String, java.lang.Boolean> switchStates, @org.jetbrains.annotations.Nullable()
    java.lang.String cameraSnapshotUrl) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    public final void setId(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFloorId() {
        return null;
    }
    
    public final void setFloorId(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    public final void setName(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.smarthome.data.model.DeviceType getType() {
        return null;
    }
    
    public final void setType(@org.jetbrains.annotations.NotNull()
    com.example.smarthome.data.model.DeviceType p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.smarthome.data.model.DeviceStatus getStatus() {
        return null;
    }
    
    public final void setStatus(@org.jetbrains.annotations.NotNull()
    com.example.smarthome.data.model.DeviceStatus p0) {
    }
    
    public final int getGridX() {
        return 0;
    }
    
    public final void setGridX(int p0) {
    }
    
    public final int getGridY() {
        return 0;
    }
    
    public final void setGridY(int p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getMaxOnDurationMinutes() {
        return null;
    }
    
    public final void setMaxOnDurationMinutes(@org.jetbrains.annotations.Nullable()
    java.lang.Integer p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getTurnedOnAt() {
        return null;
    }
    
    public final void setTurnedOnAt(@org.jetbrains.annotations.Nullable()
    java.lang.Long p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getScheduleStart() {
        return null;
    }
    
    public final void setScheduleStart(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getScheduleEnd() {
        return null;
    }
    
    public final void setScheduleEnd(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getSwitchCount() {
        return null;
    }
    
    public final void setSwitchCount(@org.jetbrains.annotations.Nullable()
    java.lang.Integer p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.Map<java.lang.String, java.lang.Boolean> getSwitchStates() {
        return null;
    }
    
    public final void setSwitchStates(@org.jetbrains.annotations.Nullable()
    java.util.Map<java.lang.String, java.lang.Boolean> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getCameraSnapshotUrl() {
        return null;
    }
    
    public final void setCameraSnapshotUrl(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    public Device() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.Map<java.lang.String, java.lang.Boolean> component13() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component14() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.smarthome.data.model.DeviceType component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.smarthome.data.model.DeviceStatus component5() {
        return null;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int component7() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.smarthome.data.model.Device copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String floorId, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    com.example.smarthome.data.model.DeviceType type, @org.jetbrains.annotations.NotNull()
    com.example.smarthome.data.model.DeviceStatus status, int gridX, int gridY, @org.jetbrains.annotations.Nullable()
    java.lang.Integer maxOnDurationMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.Long turnedOnAt, @org.jetbrains.annotations.Nullable()
    java.lang.String scheduleStart, @org.jetbrains.annotations.Nullable()
    java.lang.String scheduleEnd, @org.jetbrains.annotations.Nullable()
    java.lang.Integer switchCount, @org.jetbrains.annotations.Nullable()
    java.util.Map<java.lang.String, java.lang.Boolean> switchStates, @org.jetbrains.annotations.Nullable()
    java.lang.String cameraSnapshotUrl) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}