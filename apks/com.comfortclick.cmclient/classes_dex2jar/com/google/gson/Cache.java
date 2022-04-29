package com.google.gson;

abstract interface Cache<K, V>
{
  public abstract void addElement(K paramK, V paramV);
  
  public abstract V getElement(K paramK);
}
