package com.google.gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

abstract class RecursiveFieldNamingPolicy
  implements FieldNamingStrategy2
{
  RecursiveFieldNamingPolicy() {}
  
  public final String translateName(FieldAttributes paramFieldAttributes)
  {
    return translateName(paramFieldAttributes.getName(), paramFieldAttributes.getDeclaredType(), paramFieldAttributes.getAnnotations());
  }
  
  protected abstract String translateName(String paramString, Type paramType, Collection<Annotation> paramCollection);
}
