package com.example.smarthome.ui.device;

import androidx.lifecycle.SavedStateHandle;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class DeviceDetailViewModel_Factory implements Factory<DeviceDetailViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public DeviceDetailViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public DeviceDetailViewModel get() {
    return newInstance(savedStateHandleProvider.get());
  }

  public static DeviceDetailViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new DeviceDetailViewModel_Factory(savedStateHandleProvider);
  }

  public static DeviceDetailViewModel newInstance(SavedStateHandle savedStateHandle) {
    return new DeviceDetailViewModel(savedStateHandle);
  }
}
