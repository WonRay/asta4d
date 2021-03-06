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
package com.astamuse.asta4d.util.annotation;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ConvertableAnnotationRetriever {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static final <T extends Annotation> T retrieveAnnotation(Class<T> targetAnnotation, Annotation... annotations) {

        try {
            String targetName = targetAnnotation.getName();
            T found = null;
            ConvertableAnnotation ca;
            for (Annotation annotation : annotations) {
                while (true) {
                    if (annotation.annotationType().getName().equals(targetName)) {
                        found = (T) annotation;
                        break;
                    }
                    ca = annotation.annotationType().getAnnotation(ConvertableAnnotation.class);
                    if (ca == null) {
                        break;
                    } else {
                        AnnotationConvertor ac = ca.value().newInstance();
                        annotation = ac.convert(annotation);
                    }
                }
                if (found != null) {
                    break;
                }
            }
            return found;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 
     * 
     * @param targetAnnotation
     * @param annotations
     * @return The first element in the returned list is the target annotation specified by parameter and the next is which the previous one
     *         be converted from
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static final List<Annotation> retrieveAnnotationHierarchyList(Class<? extends Annotation> targetAnnotation,
            Annotation... annotations) {

        try {
            String targetName = targetAnnotation.getName();
            ConvertableAnnotation ca;
            List<Annotation> list = new LinkedList<>();
            for (Annotation annotation : annotations) {
                list.clear();
                while (true) {
                    list.add(annotation);
                    if (annotation.annotationType().getName().equals(targetName)) {
                        // found
                        break;
                    }
                    ca = annotation.annotationType().getAnnotation(ConvertableAnnotation.class);
                    if (ca == null) {
                        // not found
                        list.clear();
                        break;
                    } else {
                        AnnotationConvertor ac = ca.value().newInstance();
                        annotation = ac.convert(annotation);
                    }
                }// end of while
                if (!list.isEmpty()) {
                    break;
                }
            }
            Collections.reverse(list);
            return list;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
