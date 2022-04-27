package com.adobe.fre;

public class FREASErrorException
  extends Exception
{
  public static final long serialVersionUID = 1L;
  private FREObject m_thrownASException;
  
  public FREASErrorException() {}
  
  public FREObject getThrownException()
  {
    return this.m_thrownASException;
  }
}
