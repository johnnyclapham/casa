package com.activehomevista;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.preference.ListPreference;
import android.util.AttributeSet;

public class ListPreferenceMultiSelect
  extends ListPreference
{
  private static final String SEPARATOR = "]]]";
  private boolean[] mClickedDialogEntryIndices;
  
  public ListPreferenceMultiSelect(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ListPreferenceMultiSelect(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (getEntries() != null) {
      this.mClickedDialogEntryIndices = new boolean[getEntries().length];
    }
  }
  
  public static boolean isValueInList(CharSequence[] paramArrayOfCharSequence, String paramString)
  {
    if (paramArrayOfCharSequence == null) {}
    for (;;)
    {
      return false;
      for (int i = 1; i < paramArrayOfCharSequence.length; i += 2) {
        if ((paramArrayOfCharSequence[i] != null) && ((paramArrayOfCharSequence[i].equals(paramString)) || (paramArrayOfCharSequence[i].equals("\"" + paramString + "\"")))) {
          return true;
        }
      }
    }
  }
  
  public static String[] parseStoredValue(CharSequence paramCharSequence)
  {
    if ("".equals(paramCharSequence)) {
      return null;
    }
    return ((String)paramCharSequence).split("]]]");
  }
  
  private void restoreCheckedEntries()
  {
    CharSequence[] arrayOfCharSequence = getEntryValues();
    if (arrayOfCharSequence != null)
    {
      for (int i = 0; i < arrayOfCharSequence.length; i++) {
        this.mClickedDialogEntryIndices[i] = false;
      }
      if (getValue() != null)
      {
        String[] arrayOfString = parseStoredValue(getValue());
        if (arrayOfString != null)
        {
          int j = 1;
          if (j < arrayOfString.length)
          {
            String str = arrayOfString[j].trim();
            for (int k = 0;; k++) {
              if (k < arrayOfCharSequence.length)
              {
                if (arrayOfCharSequence[k].equals(str)) {
                  this.mClickedDialogEntryIndices[k] = true;
                }
              }
              else
              {
                j += 2;
                break;
              }
            }
          }
        }
      }
    }
  }
  
  protected void onDialogClosed(boolean paramBoolean)
  {
    CharSequence[] arrayOfCharSequence1 = getEntries();
    CharSequence[] arrayOfCharSequence2 = getEntryValues();
    if ((paramBoolean) && (arrayOfCharSequence2 != null))
    {
      StringBuffer localStringBuffer = new StringBuffer();
      for (int i = 0; i < arrayOfCharSequence2.length; i++) {
        if (this.mClickedDialogEntryIndices[i] != 0)
        {
          localStringBuffer.append(arrayOfCharSequence1[i]).append("]]]");
          localStringBuffer.append(arrayOfCharSequence2[i]).append("]]]");
        }
      }
      if (callChangeListener(localStringBuffer))
      {
        String str = localStringBuffer.toString();
        if (str.length() > 0) {
          str = str.substring(0, str.length() - "]]]".length());
        }
        setValue(str);
      }
      return;
    }
    restoreCheckedEntries();
  }
  
  protected void onPrepareDialogBuilder(AlertDialog.Builder paramBuilder)
  {
    CharSequence[] arrayOfCharSequence1 = getEntries();
    CharSequence[] arrayOfCharSequence2 = getEntryValues();
    if ((arrayOfCharSequence1 == null) || (arrayOfCharSequence2 == null) || (arrayOfCharSequence1.length != arrayOfCharSequence2.length)) {
      throw new IllegalStateException("ListPreferenceMultiSelect requires an entries array and an entry values array which are both the same length");
    }
    restoreCheckedEntries();
    paramBuilder.setMultiChoiceItems(arrayOfCharSequence1, this.mClickedDialogEntryIndices, new DialogInterface.OnMultiChoiceClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        ListPreferenceMultiSelect.this.mClickedDialogEntryIndices[paramAnonymousInt] = paramAnonymousBoolean;
      }
    });
  }
  
  public void setEntries(CharSequence[] paramArrayOfCharSequence)
  {
    super.setEntries(paramArrayOfCharSequence);
    if (paramArrayOfCharSequence != null) {
      this.mClickedDialogEntryIndices = new boolean[paramArrayOfCharSequence.length];
    }
  }
}
