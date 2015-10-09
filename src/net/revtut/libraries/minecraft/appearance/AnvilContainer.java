package net.revtut.libraries.minecraft.appearance;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ContainerAnvil;
import net.minecraft.server.v1_8_R3.EntityHuman;

/**
 * Anvil Container
 */
public class AnvilContainer extends ContainerAnvil {

    /**
     * Constructor of AnvilContainer
     * @param entity entity to open the anvil container
     */
    public AnvilContainer(final EntityHuman entity) {
        super(entity.inventory, entity.world, new BlockPosition(0, 0, 0), entity);
    }

    /**
     * Check if entity can access anvil
     * @param entityhuman human trying to access anvil
     * @return true if can, false otherwise
     */
    @Override
    public boolean a(final EntityHuman entityhuman) {
        return true;
    }
}
