package slimeknights.tconstruct.library.tinkering;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.common.config.Config;

import javax.annotation.Nonnull;

public class IndestructibleEntityItem extends EntityItem {

  public IndestructibleEntityItem(World worldIn, double x, double y, double z) {
    super(worldIn, x, y, z);
    if (Config.invulnerableTools) {
      isImmuneToFire = true;
    }
  }

  public IndestructibleEntityItem(World worldIn, double x, double y, double z, ItemStack stack) {
    super(worldIn, x, y, z, stack);
    if (Config.invulnerableTools) {
      isImmuneToFire = true;
    }
  }

  public IndestructibleEntityItem(World worldIn) {
    super(worldIn);
    if (Config.invulnerableTools) {
      isImmuneToFire = true;
    }
  }

  @Override
  public boolean attackEntityFrom(@Nonnull DamageSource source, float amount) {
    if (! Config.invulnerableTools) {
      return super.attackEntityFrom(source, amount);
    }
    if(source.getDamageType().equals(DamageSource.OUT_OF_WORLD.damageType)) {
      return true;
    }
    // prevent any damage besides out of world
    return false;
  }

  public static class EventHandler {

    public static final EventHandler instance = new EventHandler();

    private EventHandler() {
    }

    @SubscribeEvent
    public void onExpire(ItemExpireEvent event) {
      if(event.getEntityItem() instanceof IndestructibleEntityItem) {
        event.setCanceled(true);
      }
    }
  }
}
