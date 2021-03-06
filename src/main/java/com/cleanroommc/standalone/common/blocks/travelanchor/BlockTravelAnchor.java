package com.cleanroommc.standalone.common.blocks.travelanchor;

import com.cleanroommc.standalone.Standalone;
import com.cleanroommc.standalone.api.StandaloneBlock;
import com.cleanroommc.standalone.client.gui.GuiHandler;
import com.cleanroommc.standalone.client.render.ITESRBlock;
import com.cleanroommc.standalone.client.render.renderers.TravelSpecialRenderer;
import com.cleanroommc.standalone.common.StandaloneConfig;
import com.cleanroommc.standalone.common.tileentity.travelanchor.TileEntityTravelAnchor;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BlockTravelAnchor extends StandaloneBlock implements ITileEntityProvider, ITESRBlock {

    public BlockTravelAnchor() {
        super(new BlockSettings(Material.IRON)
                .strength(2.5f)
                .soundType(SoundType.STONE)
                .creativeTab(CreativeTabs.TRANSPORTATION)
                .translationKey("travel_anchor")
        );
    }

    @Override
    public boolean onBlockActivated(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state,
                                    @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing,
                                    float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            playerIn.openGui(Standalone.INSTANCE, GuiHandler.GUI_TRAVEL_ANCHOR, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean eventReceived(@Nonnull IBlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, int id, int param) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }

    @Override
    public boolean hasTileEntity(@Nonnull IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileEntityTravelAnchor();
    }

    @Override
    public boolean isOpaqueCube(@Nonnull IBlockState bs) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void bindTileEntitySpecialRenderer() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTravelAnchor.class, new TravelSpecialRenderer<>());
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            if (StandaloneConfig.travel.allowJumping && StandaloneConfig.travel.allowSneaking)
                tooltip.add(I18n.format("standalone.travel_anchor.jump_sneak_teleport"));
            else if (StandaloneConfig.travel.allowJumping)
                tooltip.add(I18n.format("standalone.travel_anchor.jump_teleport"));
            else if (StandaloneConfig.travel.allowSneaking)
                tooltip.add(I18n.format("standalone.travel_anchor.sneak_teleport"));
            tooltip.add(I18n.format("standalone.travel_anchor.elevator"));
        } else {
            tooltip.add(I18n.format("standalone.hold_shift"));
        }
    }
}
