/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Elior Boukhobza
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 *
 */

package com.mallowigi.search.parsers;

import com.mallowigi.utils.ColorUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.StringTokenizer;

public final class RGBColorParser implements ColorParser {
  /**
   * Parse a color in the rgb[a](r, g, b[, a]) format
   */
  @SuppressWarnings({
    "ReuseOfLocalVariable",
    "UseOfStringTokenizer",
    "DuplicatedCode",
    "OverlyLongMethod"})
  @Nullable
  private static Color parseRGB(@NotNull final String text) {
    boolean isPercent = false;
    float a = 1.0f;
    final int red;
    final int green;
    final int blue;
    final int parenStart = text.indexOf(ColorUtils.OPEN_PAREN);
    final int parenEnd = text.indexOf(ColorUtils.CLOSE_PAREN);

    if (parenStart == -1 || parenEnd == -1) {
      return null;
    }

    final StringTokenizer tokenizer = new StringTokenizer(text.substring(parenStart + 1, parenEnd), ColorUtils.COMMA); // split by ,
    if (tokenizer.countTokens() < 3) {
      return null;
    }

    // Parse r, g, b and a
    String part = tokenizer.nextToken().trim();
    if (part.endsWith(ColorUtils.PERCENT)) {
      isPercent = true;
      red = Integer.parseInt(part.substring(0, part.length() - 1));
    } else {
      red = Integer.parseInt(part);
    }

    part = tokenizer.nextToken().trim();
    if (part.endsWith(ColorUtils.PERCENT)) {
      isPercent = true;
      green = Integer.parseInt(part.substring(0, part.length() - 1));
    } else {
      green = Integer.parseInt(part);
    }

    part = tokenizer.nextToken().trim();
    if (part.endsWith(ColorUtils.PERCENT)) {
      isPercent = true;
      blue = Integer.parseInt(part.substring(0, part.length() - 1));
    } else {
      blue = Integer.parseInt(part);
    }

    if (tokenizer.hasMoreTokens()) {
      part = tokenizer.nextToken().trim();
      a = Float.parseFloat(part);
    }

    return isPercent ? ColorUtils.getPercentRGBa(red, green, blue, a) : ColorUtils.getDecimalRGBa(red, green, blue, a);
  }

  @Override
  public Color parseColor(final String text) {
    return parseRGB(text);
  }
}
