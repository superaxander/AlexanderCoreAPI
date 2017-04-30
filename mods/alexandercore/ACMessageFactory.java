package alexanders.mods.alexandercore;

import alexanders.mods.alexandercore.handler.IEventHandler;
import alexanders.mods.alexandercore.handler.IHookCollector;
import com.google.common.base.Function;
import net.minecraftforge.fml.common.event.FMLInterModComms;

import java.util.ArrayList;

public class ACMessageFactory
{
    private static final ArrayList<IEventHandler> toBeRegisteredHandlers = new ArrayList<>();
    private static final Object lock = new Object();

    public static void registerEventHandler(IEventHandler handler, String modid)
    {
        System.out.println(Function.class.isAssignableFrom(ACFunction.class));
        synchronized (lock)
        {
            boolean b = FMLInterModComms.sendFunctionMessage("alexandercore", "register", "alexanders.mods.alexandercore.ACMessageFactory.ACFunction");
            if(!b)
                FMLInterModComms.sendRuntimeFunctionMessage("alexandercore", modid, "register", "alexanders.mods.alexandercore.ACMessageFactory.ACFunction");
            System.out.println(b ? "Sent normally" : "Sent at runtime");
        }
    }

    public class ACFunction implements Function<IHookCollector, Boolean>
    {
        @Override 
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
