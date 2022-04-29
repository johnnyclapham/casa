package com.google.gson;

final class SyntheticFieldExclusionStrategy
  implements ExclusionStrategy
{
  private final boolean skipSyntheticFields;
  
  SyntheticFieldExclusionStrategy(boolean paramBoolean)
  {
    this.skipSyntheticFields = paramBoolean;
  }
  
  public boolean shouldSkipClass(Class<?> paramClass)
  {
    return false;
  }
  
  public boolean shouldSkipField(FieldAttributes paramFieldAttributes)
  {
    return (this.skipSyntheticFields) && (paramFieldAttributes.isSynthetic());
  }
}
