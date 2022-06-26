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

    // Now supporting overflowing Integer.MAX_VALUE. (solution by @BloCamLimb)
    @Override
    public int receiveEnergy(int maxReceived, boolean simulate) {
        final int stored = getEnergyStored();

        final int received = Math.max(0, Math.min(maxReceived, getMaxEnergyStored() - stored));

        if (!simulate)
            stack.getOrCreateTag().putInt("energy", stored + received);

        return received;
    }

    @Override
    public int extractEnergy(int maxExtracted, boolean simulate) {
        final int stored = getEnergyStored();

        final int extracted = Math.max(0, Math.min(maxExtracted, stored));

        if (!simulate)
            stack.getOrCreateTag().putInt("energy", stored - extracted);

        return extracted;
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
