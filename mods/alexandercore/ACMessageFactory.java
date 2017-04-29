package alexanders.mods.alexandercore;

import alexanders.mods.alexandercore.handler.IEventHandler;
import alexanders.mods.alexandercore.handler.IHookCollector;
import com.google.common.base.Function;
import com.sun.istack.internal.NotNull;
import net.minecraftforge.fml.common.event.FMLInterModComms;

import java.util.ArrayList;

public class ACMessageFactory
{
    private static final ArrayList<IEventHandler> toBeRegisteredHandlers = new ArrayList<>();
    private static final Object lock = new Object();

    public static void registerEventHandler(IEventHandler handler)
    {
        synchronized (lock)
        {
            FMLInterModComms.sendFunctionMessage("alexandercore", "register", ACFunction.class.getName());
        }
    }

    public class ACFunction implements Function<IHookCollector, Boolean>
    {
        @Override
        @NotNull
        public Boolean apply(IHookCollector o)
        {
            try
            {
                synchronized (lock)
                {
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
}