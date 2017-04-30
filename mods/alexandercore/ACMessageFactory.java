package alexanders.mods.alexandercore;

import alexanders.mods.alexandercore.handler.IEventHandler;
import alexanders.mods.alexandercore.handler.IHookCollector;
import com.google.common.base.Function;
import net.minecraftforge.fml.common.event.FMLInterModComms;

import java.util.ArrayList;

public class ACMessageFactory
{
    static final ArrayList<IEventHandler> toBeRegisteredHandlers = new ArrayList<>();
    static final Object lock = new Object();

    public static void registerEventHandler(IEventHandler handler, String modid)
    {
        synchronized (lock)
        {
            toBeRegisteredHandlers.add(handler);
            boolean b = FMLInterModComms.sendFunctionMessage("alexandercore", "register", "alexanders.mods.alexandercore.ACFunction");
            if(!b)
                FMLInterModComms.sendRuntimeFunctionMessage("alexandercore", modid, "register", "alexanders.mods.alexandercore.ACFunction");
            System.out.println(b ? "Sent normally" : "Sent at runtime");
        }
    }


}
