package alexanders.mods.alexandercore.handler;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;

public interface IPreTransformSideFirstPersonHandler extends IEventHandler
{
    boolean handle(ItemStack stack, EnumHandSide handSide, float partialTicks, float equipProgress);
}
