package com.google.gson;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

final class ModifierBasedExclusionStrategy
  implements ExclusionStrategy
{
  private final Collection<Integer> modifiers = new HashSet();
  
  public ModifierBasedExclusionStrategy(int... paramVarArgs)
  {
    if (paramVarArgs != null)
    {
      int i = paramVarArgs.length;
      for (int j = 0; j < i; j++)
      {
        int k = paramVarArgs[j];
        this.modifiers.add(Integer.valueOf(k));
      }
    }
  }
  
  public boolean shouldSkipClass(Class<?> paramClass)
  {
    return false;
  }
  
  public boolean shouldSkipField(FieldAttributes paramFieldAttributes)
  {
    Iterator localIterator = this.modifiers.iterator();
    while (localIterator.hasNext()) {
      if (paramFieldAttributes.hasModifier(((Integer)localIterator.next()).intValue())) {
        return true;
      }
    }
    return false;
  }
}
