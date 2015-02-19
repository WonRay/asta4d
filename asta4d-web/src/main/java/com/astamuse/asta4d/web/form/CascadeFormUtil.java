/*
 * Copyright 2014 astamuse company,Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.astamuse.asta4d.web.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CascadeFormUtil {

    public static final Integer[] ROOT_OF_INDEXES = new Integer[0];

    public static final String rewriteArrayIndexPlaceHolder(String s, Integer[] indexes) {
        if (indexes != null) {
            String prefix = "(?<=-)";
            String target = "@";
            String suffix = "(?!@)";
            for (int i = 0; i < indexes.length; i++, target += "@") {
                s = s.replaceFirst(prefix + target + suffix, indexes[i].toString());
            }
        }
        return s;
    }

    public static final Integer[] addIndex(Integer[] indexes, int index) {
        List<Integer> arrayIndexes = new ArrayList<Integer>(Arrays.asList(indexes));
        arrayIndexes.add(Integer.valueOf(index));
        return arrayIndexes.toArray(ROOT_OF_INDEXES);
    }
}