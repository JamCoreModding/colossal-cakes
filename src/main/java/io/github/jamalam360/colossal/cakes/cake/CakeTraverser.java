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

import java.util.ArrayDeque;
import java.util.HashSet;

/**
 * @author Falkreon - safe implementation
 */

public class CakeTraverser {
    private static final long MAX_ITERATIONS = 3000L;

    public static boolean safeTraverse(BlockView world, BlockPos start) {
        if (!world.getBlockState(start).isIn(ColossalCakesTags.CAKE) || Cake.get(start) != null) return false;

        Cake cake = new Cake();

        safeTraverse(world, cake, start);

        if (cake.getPositions().isEmpty()) {
            cake.discard();
            return false;
        }

        if (world instanceof ServerWorld serverWorld) {
            CakeState.get(serverWorld).markDirty();
        }

        return true;
    }

    public static void safeTraverse(BlockView blockView, Cake cake, BlockPos start) {
        HashSet<BlockPos> traversed = new HashSet<>(); // HashSet has very good contains performance
        ArrayDeque<BlockPos> workQueue = new ArrayDeque<>(); // Very good performance for adding/removing at either end
        workQueue.add(start.toImmutable());
        long iterations = 0L;
        BlockPos.Mutable cur = start.mutableCopy();

        while (!workQueue.isEmpty()) {
            BlockPos workItem = workQueue.removeFirst();

            for (Direction dir : Direction.values()) {
                // Similar to move but without the need to move back or reallocate the mutable
                cur.set(workItem.getX() + dir.getOffsetX(), workItem.getY() + dir.getOffsetY(), workItem.getZ() + dir.getOffsetZ());

                if (!traversed.contains(cur)) {
                    BlockPos curImmutable = cur.toImmutable();
                    traversed.add(curImmutable);

                    Cake existing = Cake.get(cur);

                    if (existing != null) {
                        cake.add(curImmutable);
                        cake.addAll(existing.getPositions());
                        existing.discard();
                        workQueue.add(curImmutable);
                    } else if (blockView.getBlockState(cur).isIn(ColossalCakesTags.CAKE)) {
                        cake.add(curImmutable);
                        workQueue.add(curImmutable);
                    }
                }
            }

            iterations++;
            if (iterations > MAX_ITERATIONS) {
                return;
            }
        }
    }
}
