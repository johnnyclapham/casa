package so.cuo.anes.admob.functions;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;
import so.cuo.anes.admob.AdMobContext;

public class AddToStageFunction
  implements FREFunction
{
  public AddToStageFunction() {}
  
  public FREObject call(FREContext paramFREContext, FREObject[] paramArrayOfFREObject)
  {
    FREObject localFREObject1 = paramArrayOfFREObject[0];
    FREObject localFREObject2 = paramArrayOfFREObject[1];
    try
    {
      int i = localFREObject1.getAsInt();
      int j = localFREObject2.getAsInt();
      ((AdMobContext)paramFREContext).addToStage(i, j);
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
