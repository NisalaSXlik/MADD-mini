package com.example.smarthome.ui.device;

import androidx.lifecycle.SavedStateHandle;
import com.example.smarthome.data.repository.SmartHomeRepository;
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

  private final Provider<SmartHomeRepository> repositoryProvider;

  public DeviceDetailViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<SmartHomeRepository> repositoryProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DeviceDetailViewModel get() {
    return newInstance(savedStateHandleProvider.get(), repositoryProvider.get());
  }

  public static DeviceDetailViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<SmartHomeRepository> repositoryProvider) {
    return new DeviceDetailViewModel_Factory(savedStateHandleProvider, repositoryProvider);
  }

  public static DeviceDetailViewModel newInstance(SavedStateHandle savedStateHandle,
      SmartHomeRepository repository) {
    return new DeviceDetailViewModel(savedStateHandle, repository);
  }
}
