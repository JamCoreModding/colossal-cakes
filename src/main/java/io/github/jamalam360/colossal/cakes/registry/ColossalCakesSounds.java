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

package io.github.jamalam360.colossal.cakes.registry;

import io.github.jamalam360.colossal.cakes.ColossalCakesInit;
import io.github.jamalam360.jamlib.registry.annotation.ContentRegistry;
import net.minecraft.sound.SoundEvent;

/**
 * @author Jamalam
 */

@ContentRegistry(ColossalCakesInit.MOD_ID)
public class ColossalCakesSounds {
    public static final SoundEvent ITEM_ROLLING_PIN_BONK = new SoundEvent(ColossalCakesInit.idOf("item_rolling_pin_bonk"));
    public static final SoundEvent ITEM_ROLLING_PIN_BONK_SWEEP = new SoundEvent(ColossalCakesInit.idOf("item_rolling_pin_bonk_sweep"));
    public static final SoundEvent ITEM_ROLLING_PIN_USE = new SoundEvent(ColossalCakesInit.idOf("item_rolling_pin_use"));
    public static final SoundEvent ITEM_WHISK_USE = new SoundEvent(ColossalCakesInit.idOf("item_whisk_use"));
}
