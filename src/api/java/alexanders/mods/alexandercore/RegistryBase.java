package alexanders.mods.alexandercore;

import java.util.HashMap;
import org.apache.logging.log4j.Logger;

public abstract class RegistryBase<T>
{
    protected final HashMap<String, T> registry;
    protected final String name;
    protected final Logger modLogger;

    protected boolean hasRegistered;

    protected RegistryBase(String name, Logger modLogger)
    {
        this.name = name;
        this.registry = new HashMap<>();
	this.modLogger = modLogger;
    }

    protected void addAll(HashMap<String, T> base)
    {
        registry.putAll(base);
        if (hasRegistered)
            base.forEach(this::register);
    }

    protected void add(String name, T registrant)
    {
        registry.put(name, registrant);
        if (hasRegistered)
            register(name, registrant);
    }

    public void register()
    {
        this.hasRegistered = true;
        registerInternal();
    }

    protected void registerInternal()
    {
        modLogger.debug("Registry " + name + " is using standard registry methods");
        registry.forEach(this::register);
    }

    private void register(String name, T registrant)
    {
        modLogger.debug("Registry " + name + " is registering: " + registrant.toString());
        registerInternal(name, registrant);
    }

    protected abstract void registerInternal(String name, T registrant);

    public T get(String name)
    {
        return registry.get(name);
    }
}
