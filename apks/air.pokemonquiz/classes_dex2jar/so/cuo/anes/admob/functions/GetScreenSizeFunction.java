package so.cuo.anes.admob.functions;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREWrongThreadException;
import so.cuo.anes.admob.AdMobContext;

public class GetScreenSizeFunction
  implements FREFunction
{
  public GetScreenSizeFunction() {}
  
  public FREObject call(FREContext paramFREContext, FREObject[] paramArrayOfFREObject)
  {
    AdMobContext localAdMobContext = (AdMobContext)paramFREContext;
    try
    {
      FREObject localFREObject = FREObject.newObject(localAdMobContext.getScreenSize());
      return localFREObject;
    }
    catch (FREWrongThreadException localFREWrongThreadException)
    {
      localFREWrongThreadException.printStackTrace();
    }
    return null;
  }
}
