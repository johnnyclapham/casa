package com.google.gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

final class LowerCaseNamingPolicy
  extends RecursiveFieldNamingPolicy
{
  LowerCaseNamingPolicy() {}
  
  protected String translateName(String paramString, Type paramType, Collection<Annotation> paramCollection)
  {
    return paramString.toLowerCase();
  }
}
