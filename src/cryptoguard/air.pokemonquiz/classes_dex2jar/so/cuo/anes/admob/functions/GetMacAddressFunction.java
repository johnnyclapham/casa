package so.cuo.anes.admob.functions;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREWrongThreadException;
import so.cuo.anes.admob.AdMobContext;

public class GetMacAddressFunction
  implements FREFunction
{
  public GetMacAddressFunction() {}
  
  public FREObject call(FREContext paramFREContext, FREObject[] paramArrayOfFREObject)
  {
    String str = ((AdMobContext)paramFREContext).getMacAddress();
    try
    {
      FREObject localFREObject = FREObject.newObject(str);
      return localFREObject;
    }
    catch (FREWrongThreadException localFREWrongThreadException)
    {
      localFREWrongThreadException.printStackTrace();
    }
    return null;
  }
}
