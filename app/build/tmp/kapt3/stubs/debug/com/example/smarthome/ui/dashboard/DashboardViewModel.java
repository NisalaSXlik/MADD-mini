package com.example.smarthome.ui.dashboard;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014J\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0017\u001a\u00020\u0012R\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00050\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\f\u00a8\u0006\u0018"}, d2 = {"Lcom/example/smarthome/ui/dashboard/DashboardViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_devices", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/example/smarthome/data/model/Device;", "_floors", "Lcom/example/smarthome/data/model/Floor;", "devices", "Lkotlinx/coroutines/flow/StateFlow;", "getDevices", "()Lkotlinx/coroutines/flow/StateFlow;", "floors", "getFloors", "addFloor", "", "name", "", "gridRows", "", "gridCols", "getDevicesForFloor", "floorId", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class DashboardViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.smarthome.data.model.Floor>> _floors = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.smarthome.data.model.Floor>> floors = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.smarthome.data.model.Device>> _devices = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.smarthome.data.model.Device>> devices = null;
    
    @javax.inject.Inject()
    public DashboardViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.smarthome.data.model.Floor>> getFloors() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.smarthome.data.model.Device>> getDevices() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.smarthome.data.model.Device> getDevicesForFloor(@org.jetbrains.annotations.NotNull()
    java.lang.String floorId) {
        return null;
    }
    
    public final void addFloor(@org.jetbrains.annotations.NotNull()
    java.lang.String name, int gridRows, int gridCols) {
    }
}