package so.cuo.anes.admob.functions;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;
import so.cuo.anes.admob.AdMobContext;

public class LoadADFunction
  implements FREFunction
{
  public LoadADFunction() {}
  
  public FREObject call(FREContext paramFREContext, FREObject[] paramArrayOfFREObject)
  {
    FREObject localFREObject = paramArrayOfFREObject[0];
    try
    {
      boolean bool = localFREObject.getAsBool();
      ((AdMobContext)paramFREContext).loadAD(bool);
      return null;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      for (;;)
      {
        localIllegalStateException.printStackTrace();
      }
    }
    catch (FRETypeMismatchException localFRETypeMismatchException)
    {
      for (;;)
      {
        localFRETypeMismatchException.printStackTrace();
      }
    }
    catch (FREInvalidObjectException localFREInvalidObjectException)
    {
      for (;;)
      {
        localFREInvalidObjectException.printStackTrace();
      }
    }
    catch (FREWrongThreadException localFREWrongThreadException)
    {
      for (;;)
      {
        localFREWrongThreadException.printStackTrace();
      }
    }
  }
}
