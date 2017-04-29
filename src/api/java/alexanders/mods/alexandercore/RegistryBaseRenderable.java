package alexanders.mods.alexandercore;

import java.util.HashMap;
import org.apache.logging.log4j.Logger;

public abstract class RegistryBaseRenderable<T> extends RegistryBase<T>
{
    private boolean hasRegisteredRender;

    protected RegistryBaseRenderable(String name, Logger modLogger)
    {
        super(name, modLogger);
        this.hasRegistered = this.hasRegisteredRender = false;
    }

    protected void addAll(HashMap<String, T> base)
    {
        super.addAll(base);
        if (hasRegisteredRender)
        {
            base.forEach((name, registrant) ->
                         {
                             if (hasRegisteredRender)
                             {
                                 registerRender(name, registrant);
                             }
                         });
        }
    }

    protected void add(String name, T registrant)
    {
        super.add(name, registrant);
        if (hasRegisteredRender)
        {
            registerRender(name, registrant);
        }
    }

    public void registerRender()
    {
        this.hasRegisteredRender = true;
        registerRenderInternal();
    }

    private void registerRender(String name, T registrant)
    {
        modLogger.debug("Registry " + name + " is registering render: " + registrant.toString());
        registerRenderInternal(name, registrant);
    }

    protected void registerRenderInternal()
    {
        modLogger.debug("Registry " + name + " is using standard render registry methods");
        registry.forEach(this::registerRender);
    }

    protected abstract void registerRenderInternal(String name, T registrant);
}
