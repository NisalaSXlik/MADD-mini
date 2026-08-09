package com.example.smarthome.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rJ\u0012\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u000fJ\u0012\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00100\u000fJ\u000e\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u0011R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/example/smarthome/data/repository/SmartHomeRepository;", "", "()V", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "database", "Lcom/google/firebase/database/FirebaseDatabase;", "addFloor", "", "floor", "Lcom/example/smarthome/data/model/Floor;", "logUsage", "log", "Lcom/example/smarthome/data/model/UsageLog;", "observeDevices", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/example/smarthome/data/model/Device;", "observeFloors", "updateDevice", "device", "app_debug"})
public final class SmartHomeRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.database.FirebaseDatabase database = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    
    @javax.inject.Inject()
    public SmartHomeRepository() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.example.smarthome.data.model.Floor>> observeFloors() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.example.smarthome.data.model.Device>> observeDevices() {
        return null;
    }
    
    public final void addFloor(@org.jetbrains.annotations.NotNull()
    com.example.smarthome.data.model.Floor floor) {
    }
    
    public final void updateDevice(@org.jetbrains.annotations.NotNull()
    com.example.smarthome.data.model.Device device) {
    }
    
    public final void logUsage(@org.jetbrains.annotations.NotNull()
    com.example.smarthome.data.model.UsageLog log) {
    }
}