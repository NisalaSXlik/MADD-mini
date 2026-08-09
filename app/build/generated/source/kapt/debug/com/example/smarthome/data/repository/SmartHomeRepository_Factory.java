package com.example.smarthome.data.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class SmartHomeRepository_Factory implements Factory<SmartHomeRepository> {
  @Override
  public SmartHomeRepository get() {
    return newInstance();
  }

  public static SmartHomeRepository_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SmartHomeRepository newInstance() {
    return new SmartHomeRepository();
  }

  private static final class InstanceHolder {
    private static final SmartHomeRepository_Factory INSTANCE = new SmartHomeRepository_Factory();
  }
}
