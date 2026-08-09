package com.example.smarthome.ui.device;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\rH\u0002J\u0006\u0010\u0011\u001a\u00020\u000fJ\u000e\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0017J\u0016\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\u0017J\u000e\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cJ\u0016\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\r2\u0006\u0010\u001f\u001a\u00020\rR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/example/smarthome/ui/device/DeviceDetailViewModel;", "Landroidx/lifecycle/ViewModel;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "repository", "Lcom/example/smarthome/data/repository/SmartHomeRepository;", "(Landroidx/lifecycle/SavedStateHandle;Lcom/example/smarthome/data/repository/SmartHomeRepository;)V", "device", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/example/smarthome/data/model/Device;", "getDevice", "()Lkotlinx/coroutines/flow/StateFlow;", "deviceId", "", "logUsage", "", "action", "refreshCameraSnapshot", "setDeviceStatus", "newStatus", "Lcom/example/smarthome/data/model/DeviceStatus;", "toggleDeviceStatus", "isOn", "", "toggleMultiSwitch", "switchKey", "updateMaxOnDuration", "minutes", "", "updateSchedule", "start", "end", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class DeviceDetailViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.smarthome.data.repository.SmartHomeRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String deviceId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.smarthome.data.model.Device> device = null;
    
    @javax.inject.Inject()
    public DeviceDetailViewModel(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.SavedStateHandle savedStateHandle, @org.jetbrains.annotations.NotNull()
    com.example.smarthome.data.repository.SmartHomeRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.smarthome.data.model.Device> getDevice() {
        return null;
    }
    
    public final void toggleDeviceStatus(boolean isOn) {
    }
    
    public final void setDeviceStatus(@org.jetbrains.annotations.NotNull()
    com.example.smarthome.data.model.DeviceStatus newStatus) {
    }
    
    public final void toggleMultiSwitch(@org.jetbrains.annotations.NotNull()
    java.lang.String switchKey, boolean isOn) {
    }
    
    public final void updateMaxOnDuration(int minutes) {
    }
    
    public final void updateSchedule(@org.jetbrains.annotations.NotNull()
    java.lang.String start, @org.jetbrains.annotations.NotNull()
    java.lang.String end) {
    }
    
    public final void refreshCameraSnapshot() {
    }
    
    private final void logUsage(java.lang.String action) {
    }
}