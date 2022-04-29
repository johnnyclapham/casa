package com.google.gson;

public enum FieldNamingPolicy
{
  private final FieldNamingStrategy2 namingPolicy;
  
  static
  {
    LOWER_CASE_WITH_UNDERSCORES = new FieldNamingPolicy("LOWER_CASE_WITH_UNDERSCORES", 2, new LowerCamelCaseSeparatorNamingPolicy("_"));
    LOWER_CASE_WITH_DASHES = new FieldNamingPolicy("LOWER_CASE_WITH_DASHES", 3, new LowerCamelCaseSeparatorNamingPolicy("-"));
    FieldNamingPolicy[] arrayOfFieldNamingPolicy = new FieldNamingPolicy[4];
    arrayOfFieldNamingPolicy[0] = UPPER_CAMEL_CASE;
    arrayOfFieldNamingPolicy[1] = UPPER_CAMEL_CASE_WITH_SPACES;
    arrayOfFieldNamingPolicy[2] = LOWER_CASE_WITH_UNDERSCORES;
    arrayOfFieldNamingPolicy[3] = LOWER_CASE_WITH_DASHES;
    $VALUES = arrayOfFieldNamingPolicy;
  }
  
  private FieldNamingPolicy(FieldNamingStrategy2 paramFieldNamingStrategy2)
  {
    this.namingPolicy = paramFieldNamingStrategy2;
  }
  
  FieldNamingStrategy2 getFieldNamingPolicy()
  {
    return this.namingPolicy;
  }
}
