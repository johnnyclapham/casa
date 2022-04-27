package com.adobe.air;

import android.content.res.Resources.NotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Hashtable;

public class ResourceIdMap
{
  private static Hashtable<String, Hashtable<String, Integer>> s_resourceMap;
  private Hashtable<String, Integer> m_resourceIds;
  
  public ResourceIdMap(Class<?> paramClass)
  {
    init(paramClass);
  }
  
  public ResourceIdMap(String paramString)
  {
    try
    {
      init(Class.forName(paramString));
      return;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      System.out.format("Class not found:  %s%n%n", new Object[] { paramString });
    }
  }
  
  private static void gatherResourceInfo(Class<?> paramClass, Hashtable<String, Integer> paramHashtable)
  {
    Class[] arrayOfClass = paramClass.getClasses();
    int i = arrayOfClass.length;
    int j = 0;
    Class localClass;
    String str1;
    String str2;
    label55:
    Field[] arrayOfField;
    int m;
    if (j < i)
    {
      localClass = arrayOfClass[j];
      str1 = localClass.getName();
      int k = 1 + str1.lastIndexOf('$');
      if (k <= 0) {
        break label210;
      }
      str2 = str1.substring(k);
      arrayOfField = localClass.getDeclaredFields();
      m = arrayOfField.length;
    }
    for (int n = 0;; n++)
    {
      if (n < m)
      {
        Field localField = arrayOfField[n];
        try
        {
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = str2;
          arrayOfObject[1] = localField.getName();
          String str3 = String.format("%s.%s", arrayOfObject).intern();
          if (paramHashtable.containsKey(str3)) {
            System.out.format("Did not add duplicate resource key %s", new Object[] { str3 });
          } else {
            paramHashtable.put(str3, Integer.valueOf(localField.getInt(localClass)));
          }
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          System.out.format("IllegalArgumentException", new Object[0]);
        }
        catch (IllegalAccessException localIllegalAccessException)
        {
          System.out.format("IllegalAccessException", new Object[0]);
        }
      }
      j++;
      break;
      return;
      label210:
      str2 = str1;
      break label55;
    }
  }
  
  private void init(Class<?> paramClass)
  {
    if (s_resourceMap == null) {
      s_resourceMap = new Hashtable();
    }
    if (!s_resourceMap.contains(paramClass))
    {
      this.m_resourceIds = new Hashtable();
      gatherResourceInfo(paramClass, this.m_resourceIds);
      s_resourceMap.put(paramClass.getName(), this.m_resourceIds);
      return;
    }
    this.m_resourceIds = ((Hashtable)s_resourceMap.get(paramClass));
  }
  
  public int getId(String paramString)
    throws IllegalArgumentException, Resources.NotFoundException
  {
    if (paramString == null) {
      throw new IllegalArgumentException();
    }
    String str = paramString.intern();
    if (!this.m_resourceIds.containsKey(str)) {
      throw new Resources.NotFoundException(str);
    }
    return ((Integer)this.m_resourceIds.get(str)).intValue();
  }
}
