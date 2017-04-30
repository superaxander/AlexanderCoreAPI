package alexanders.mods.alexandercore;

import alexanders.mods.alexandercore.handler.IEventHandler;
import alexanders.mods.alexandercore.handler.IHookCollector;
import com.google.common.base.Function;

import java.util.Arrays;

import static alexanders.mods.alexandercore.ACMessageFactory.lock;
import static alexanders.mods.alexandercore.ACMessageFactory.toBeRegisteredHandlers;

public class ACFunction implements Function<IHookCollector, Boolean>
{
    @Override
    public Boolean apply(IHookCollector o)
    {
        try
        {
            synchronized (lock)
            {
                System.out.println("Applying: "+ Arrays.toString(toBeRegisteredHandlers.toArray()));
                for (IEventHandler handler : toBeRegisteredHandlers)
                    o.addEventHandler(handler);
                toBeRegisteredHandlers.clear();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
