/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022 Jamalam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.jamalam360.colossal.cakes.cake;

import io.github.jamalam360.colossal.cakes.registry.ColossalCakesTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jamalam
 */
public class CakeTraverser {
    public static boolean traverse(World world, BlockPos start) {
        if (!world.getBlockState(start).isIn(ColossalCakesTags.CAKE) || Cake.get(start) != null) return false;

        Cake cake = new Cake();
        cake.add(start);
        ArrayList<BlockPos> explored = new ArrayList<>();
        explored.add(start);
        traverse(world, cake, explored, start);

        if (cake.getPositions().size() == 0) {
            cake.discard();
            return false;
        }

        if (world instanceof ServerWorld serverWorld) {
            CakeState.get(serverWorld).markDirty();
        }

        return true;
    }

    private static void traverse(BlockView blockView, Cake cake, List<BlockPos> explored, BlockPos start) {
        if (explored.size() > 400) return;

        for (Direction dir : Direction.values()) {
            BlockPos.Mutable mutable = new BlockPos.Mutable(start.getX(), start.getY(), start.getZ());
            mutable.move(dir);

            if (explored.stream().anyMatch((pos) -> pos.equals(mutable))) continue;
            explored.add(mutable);

            Cake existing = Cake.get(mutable);

            if (existing != null) {
                cake.add(mutable);
                cake.addAll(existing.getPositions());
                existing.discard();
                traverse(blockView, cake, explored, mutable);
            } else if (blockView.getBlockState(mutable).isIn(ColossalCakesTags.CAKE)) {
                cake.add(mutable);
                traverse(blockView, cake, explored, mutable);
            }
        }
    }
}
