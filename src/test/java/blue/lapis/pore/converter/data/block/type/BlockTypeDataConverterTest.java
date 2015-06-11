/*
 * Pore
 * Copyright (c) 2014-2015, Lapis <https://github.com/LapisBlue>
 *
 * The MIT License
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
package blue.lapis.pore.converter.data.block.type;

import static blue.lapis.pore.converter.data.block.type.BTDCTestUtil.testConversion;
import static blue.lapis.pore.converter.data.block.type.BTDCTestUtil.testSingleAbstraction;
import static blue.lapis.pore.converter.data.block.type.BTDCTestUtil.testSingleConversion;

import blue.lapis.pore.Pore;
import blue.lapis.pore.PoreTests;
import blue.lapis.pore.converter.data.AbstractDataValue;

import org.junit.Before;
import org.junit.Test;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.manipulator.SingleValueData;
import org.spongepowered.api.data.manipulator.block.BigMushroomData;
import org.spongepowered.api.data.manipulator.block.BrickData;
import org.spongepowered.api.data.type.BigMushroomTypes;
import org.spongepowered.api.data.type.BrickTypes;
import org.spongepowered.api.data.type.TreeTypes;
import org.spongepowered.api.util.Axis;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

@SuppressWarnings("rawtypes")
public class BlockTypeDataConverterTest {

    @Before
    public void setupEnvironment() throws Exception {
        Field instance = Pore.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, new Pore());
        Field logger = Pore.class.getDeclaredField("logger");
        logger.setAccessible(true);
        logger.set(instance.get(null), PoreTests.getLogger());
        setConstants();
    }

    public void setConstants() throws Exception {
        PoreTests.setConstants(Axis.class);
        PoreTests.setConstants(BlockTypes.class);
        PoreTests.setConstants(BigMushroomTypes.class);
        PoreTests.setConstants(BrickTypes.class);
        PoreTests.setConstants(TreeTypes.class);
    }

    @Test
    public void testBitmasking() throws Exception {
        // 245 = 5 | ((2^4 - 1) << 4), i.e. 0101 -> 11110101
        testSingleAbstraction(BlockTypes.BROWN_MUSHROOM_BLOCK, BigMushroomData.class, (byte) 245,
                BigMushroomTypes.CENTER);
    }

    @Test
    public void testBigMushroomConversion() throws Exception {
        testSingleConversion(BlockTypes.BROWN_MUSHROOM_BLOCK, BigMushroomData.class, (byte) 5,
                BigMushroomTypes.CENTER);
    }

    @Test
    public void testBrickConversion() throws Exception {
        testSingleConversion(BlockTypes.STONEBRICK, BrickData.class, (byte) 3,
                BrickTypes.CHISELED);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testLogConversion() throws Exception {
        Collection<? extends AbstractDataValue<? extends SingleValueData, ?>> expected = Arrays.asList(
                new LogDataConverter.TreeDataValue(TreeTypes.SPRUCE),
                new LogDataConverter.AxisDataValue(Axis.X)
        );
        testConversion(BlockTypes.LOG, (byte) 5, expected);
        expected = Arrays.asList(
                new LogDataConverter.TreeDataValue(TreeTypes.SPRUCE),
                new LogDataConverter.AxisDataValue(null)
        );
        testConversion(BlockTypes.LOG, (byte) 13, expected);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testLog2Conversion() throws Exception {
        Collection<? extends AbstractDataValue<? extends SingleValueData, ?>> expected = Arrays.asList(
                new LogDataConverter.TreeDataValue(TreeTypes.DARK_OAK),
                new LogDataConverter.AxisDataValue(Axis.X)
        );
        testConversion(BlockTypes.LOG2, (byte) 5, expected);
        expected = Arrays.asList(
                new LogDataConverter.TreeDataValue(TreeTypes.DARK_OAK),
                new LogDataConverter.AxisDataValue(null)
        );
        testConversion(BlockTypes.LOG2, (byte) 13, expected);
    }

}
