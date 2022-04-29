package com.google.gson;

import com.google.gson.annotations.Expose;

final class ExposeAnnotationDeserializationExclusionStrategy
  implements ExclusionStrategy
{
  ExposeAnnotationDeserializationExclusionStrategy() {}
  
  public boolean shouldSkipClass(Class<?> paramClass)
  {
    return false;
  }
  
  public boolean shouldSkipField(FieldAttributes paramFieldAttributes)
  {
    Expose localExpose = (Expose)paramFieldAttributes.getAnnotation(Expose.class);
    if (localExpose == null) {}
    while (!localExpose.deserialize()) {
      return true;
    }
    return false;
  }
}
