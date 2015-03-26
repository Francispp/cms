/* LRU
*
* Created on September 18, 2006
*
* Copyright (C) 2006 Internet Archive.
*
* This file is part of the Heritrix web crawler (crawler.archive.org).
*
* Heritrix is free software; you can redistribute it and/or modify
* it under the terms of the GNU Lesser Public License as published by
* the Free Software Foundation; either version 2.1 of the License, or
* any later version.
*
* Heritrix is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Lesser Public License for more details.
*
* You should have received a copy of the GNU Lesser Public License
* along with Heritrix; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
package com.cyberway.issue.util;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * A least-recently used cache.  As new entries are added to the map, the
 * least-recently accessed entries are removed.
 * 
 * @author pjack
 *
 * @param <K>  The key type of the LRU
 * @param <V>  The value type of the LRU
 */
public class LRU<K,V> extends LinkedHashMap<K,V> {


    /**
     * Generated by Eclipse.
     */
    private static final long serialVersionUID = 1032420936705267913L;


    /**
     * The maximum number of entries to store in the cache.
     */
    private int max;


    /**
     * Constructor.
     * 
     * @param max  the maximum number of entries to cache
     */
    public LRU(int max) {
        super(max, (float)0.75, true);
        this.max = max;
    }
    
    
    @Override
    protected boolean removeEldestEntry(Map.Entry<K,V> entry) {
        return size() >= max;
    }

}
