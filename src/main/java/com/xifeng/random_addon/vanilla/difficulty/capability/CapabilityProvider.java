package com.xifeng.random_addon.vanilla.difficulty.capability;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityProvider implements ICapabilityProvider, INBTSerializable<NBTTagCompound> {
    @CapabilityInject(IAdaptingCapability.class)
    public static final net.minecraftforge.common.capabilities.Capability<IAdaptingCapability> CAP = null;
    public static final EnumFacing DEFAULT_FACING = null;
    final Capability<IAdaptingCapability> cap;
    final IAdaptingCapability INSTANCE;
    final EnumFacing facing;

    public CapabilityProvider(IAdaptingCapability instance, Capability<IAdaptingCapability> cap, @Nullable EnumFacing facing) {
        this.cap = cap;
        this.facing = facing;
        this.INSTANCE = instance;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == this.cap;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == this.cap ? this.cap.cast(this.INSTANCE) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return this.cap != null ? (NBTTagCompound) this.cap.writeNBT(this.INSTANCE, this.facing) : new NBTTagCompound();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if (this.cap != null) {
            this.cap.readNBT(this.INSTANCE, this.facing, nbt);
        }
    }

    public static IAdaptingCapability getAdapt(Entity entity) {
        return entity != null && entity.hasCapability(CAP, DEFAULT_FACING) ? entity.getCapability(CAP, DEFAULT_FACING) : null;
    }
}
