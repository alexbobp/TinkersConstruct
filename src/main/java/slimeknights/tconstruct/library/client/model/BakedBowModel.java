package slimeknights.tconstruct.library.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.Map;

import javax.annotation.Nonnull;

import slimeknights.mantle.client.model.TRSRBakedModel;
import slimeknights.tconstruct.library.client.model.format.AmmoPosition;
import slimeknights.tconstruct.library.tools.IAmmoUser;

public class BakedBowModel extends BakedToolModel {

  protected final AmmoPosition ammoPosition;

  public BakedBowModel(IBakedModel parent,
                       BakedMaterialModel[] parts,
                       BakedMaterialModel[] brokenParts,
                       Map<String, IBakedModel> modifierParts,
                       ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transform,
                       ImmutableList<BakedToolModelOverride> overrides,
                       AmmoPosition ammoPosition) {
    super(parent, parts, brokenParts, modifierParts, transform, overrides);
    this.ammoPosition = ammoPosition;
  }

  @Nonnull
  @Override
  public ItemOverrideList getOverrides() {
    return BowItemOverrideList.INSTANCE;
  }

  protected static class BowItemOverrideList extends ToolItemOverrideList {

    static BowItemOverrideList INSTANCE = new BowItemOverrideList();

    @Override
    protected void addExtraQuads(ItemStack stack, BakedToolModel original, ImmutableList.Builder<BakedQuad> quads, World world, EntityLivingBase entityLivingBase) {
      if(original instanceof BakedBowModel && stack.getItem() instanceof IAmmoUser) {
        ItemStack ammo = ((IAmmoUser) stack.getItem()).getAmmoToRender(stack, entityLivingBase);
        if(ammo != null) {
          AmmoPosition pos = ((BakedBowModel) original).ammoPosition;
          // ammo found, render it
          IBakedModel ammoModel = ModelHelper.getBakedModelForItem(ammo, world, entityLivingBase);
          ammoModel = new TRSRBakedModel(ammoModel, pos.x, pos.y, pos.z, 0, 0, (pos.r/180f)*(float)Math.PI, 1f);
          quads.addAll(ammoModel.getQuads(null, null, 0));
        }
      }
    }
  }
}
