package com.cleanroommc.standalone.common.items;

import com.cleanroommc.standalone.api.StandaloneItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemYeeter extends StandaloneItem {

    public ItemYeeter() {
        super(new ItemSettings().creativeTab(CreativeTabs.TRANSPORTATION).maxCount(2).translationKey("yeeter"));
    }

    @Override
    @Nonnull
    public EnumAction getItemUseAction(@Nonnull ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(@Nonnull ItemStack stack) {
        return 36000;
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        ItemStack item = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, item);
    }

    @Override
    public void onPlayerStoppedUsing(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull EntityLivingBase entity, int timeLeft) {
        if (!(entity instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) entity;
        RayTraceResult rayTraceResult = rayTrace(world, player, true);
        if (rayTraceResult == null|| rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK) {
            //Ignore intellij, without the null check it will npe
            return;
        }

        float scaleFactor = Math.max((timeLeft - getMaxItemUseDuration(stack)) * 0.1f, -5.0f);
        Vec3d newVel = player.getLookVec().normalize().scale(scaleFactor);
        player.addVelocity(newVel.x, newVel.y / 2.0f, newVel.z);
        player.playSound(new SoundEvent(new ResourceLocation("minecraft", "block.slime.break")), 1.0f, 1.0f);
    }
}
