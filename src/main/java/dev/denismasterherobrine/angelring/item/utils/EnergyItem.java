package dev.denismasterherobrine.angelring.item.utils;

import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.IEnergyStorage;

public class EnergyItem implements IEnergyStorage {
    private final ItemStack stack;
    private final int capacity;

    public EnergyItem(ItemStack stack, int capacity) {
        this.stack = stack;
        this.capacity = capacity;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        final int stored = getEnergyStored();
        final int newValue = stored + maxReceive;

        final int finalValue = Math.min(Math.max(newValue, 0), getMaxEnergyStored());

        if (!simulate)
            stack.getOrCreateTag().putInt("energy", finalValue);

        return finalValue - stored;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return -receiveEnergy(-maxExtract, simulate);
    }

    @Override
    public int getEnergyStored() {
        return stack.hasTag() && stack.getTag().contains("energy") ? stack.getTag().getInt("energy") : 0;
    }

    @Override
    public int getMaxEnergyStored() {
        return capacity;
    }

    @Override
    public boolean canExtract() {
        return getEnergyStored() > 0;
    }

    @Override
    public boolean canReceive() {
        return getEnergyStored() < getMaxEnergyStored();
    }

}
