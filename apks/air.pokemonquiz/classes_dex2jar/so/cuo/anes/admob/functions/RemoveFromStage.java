package so.cuo.anes.admob.functions;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import so.cuo.anes.admob.AdMobContext;

public class RemoveFromStage
  implements FREFunction
{
  public RemoveFromStage() {}
  
  public FREObject call(FREContext paramFREContext, FREObject[] paramArrayOfFREObject)
  {
    ((AdMobContext)paramFREContext).removeFromStage();
    return null;
  }
}
