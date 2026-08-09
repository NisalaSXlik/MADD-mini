package com.example.smarthome.ui.reports;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012R\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00050\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\f\u00a8\u0006\u0013"}, d2 = {"Lcom/example/smarthome/ui/reports/ReportsViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_alerts", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/example/smarthome/data/model/Alert;", "_usageSummaries", "Lcom/example/smarthome/ui/reports/DeviceUsageSummary;", "alerts", "Lkotlinx/coroutines/flow/StateFlow;", "getAlerts", "()Lkotlinx/coroutines/flow/StateFlow;", "usageSummaries", "getUsageSummaries", "acknowledgeAlert", "", "alertId", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ReportsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.smarthome.data.model.Alert>> _alerts = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.smarthome.data.model.Alert>> alerts = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.smarthome.ui.reports.DeviceUsageSummary>> _usageSummaries = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.smarthome.ui.reports.DeviceUsageSummary>> usageSummaries = null;
    
    @javax.inject.Inject()
    public ReportsViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.smarthome.data.model.Alert>> getAlerts() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.smarthome.ui.reports.DeviceUsageSummary>> getUsageSummaries() {
        return null;
    }
    
    public final void acknowledgeAlert(@org.jetbrains.annotations.NotNull()
    java.lang.String alertId) {
    }
}