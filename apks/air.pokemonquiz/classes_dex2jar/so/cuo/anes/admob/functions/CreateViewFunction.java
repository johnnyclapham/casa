package so.cuo.anes.admob.functions;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;
import so.cuo.anes.admob.AdMobContext;

public class CreateViewFunction
  implements FREFunction
{
  public CreateViewFunction() {}
  
  public FREObject call(FREContext paramFREContext, FREObject[] paramArrayOfFREObject)
  {
    FREObject localFREObject1 = paramArrayOfFREObject[0];
    FREObject localFREObject2 = paramArrayOfFREObject[1];
    FREObject localFREObject3 = paramArrayOfFREObject[2];
    try
    {
      int i = localFREObject1.getAsInt();
      int j = localFREObject2.getAsInt();
      String str = localFREObject3.getAsString();
      ((AdMobContext)paramFREContext).createView(i, j, str);
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
