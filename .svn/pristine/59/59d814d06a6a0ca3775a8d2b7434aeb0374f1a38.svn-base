package com.cyberway.cms.component.oscache.plugins;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.oscache.base.persistence.CachePersistenceException;
import com.opensymphony.oscache.plugins.diskpersistence.AbstractDiskPersistenceListener;



public class DiskPersistenceListener extends AbstractDiskPersistenceListener {
	private Log log = LogFactory.getLog(this.getClass());
	
	/*
	 *      windows文件名不能包含下列字符\/:*?"<>|
	 *      Linux 系统区分英文字符的大小写。比如 myfile，Myfile 和 myFILE
	 * 表示的是三个不同的文件。同样，用户密码和登录名也需要区分大小写（这里沿用了 Unix 和 C
	 * 语言的命名规则）。命名目录和命名文件的规则是相同的。除非有特别的原因，否则用户创建的文件和目录名要使用小写字符。大多数的 Linux
	 * 命令也使用小写字符。
	 *      Linux 系统下的文件名长度最多可到256个字符。通常情况下，文件名的字符包括：字母、数字、“.”（点）、“_”（下划线）和“-”（连字符）。Linux
	 * 允许在文件名中使用除上述符号之外的其它符号， 但并不建议用户这样做。 有些转意字符 （即该字符被系统借用，表示某种特殊含义） 在 Linux 的
	 * 命令解释器（shell）中有特殊的含义（shell 类似于 DOS 下的命令处理器
	 * COMMAND.COM）。这样的转意字符有：“?”（问号），“*”（星号）， “ ”（空格）， “$”（货币符）， “&”，
	 * 扩号等等。在文件名中应尽量避免使用这些字符。文件名中可以有“ ”（空格），但建议用户用“_”（下划线）来替代。“/”
	 * 既可代表目录树的根也可作为路径名中的分隔符（类似DOS下的“\”），因此“/“不能出现在文件名中。
	 */
    private static final String CHARS_TO_CONVERT = ":*?\"<>| $&";

    /**
    * Build cache file name for the specified cache entry key.
    *
    * @param key   Cache Entry Key.
    * @return char[] file name.
    */
    protected char[] getCacheFileName(String key) {
        if ((key == null) || (key.length() == 0)) {
            throw new IllegalArgumentException("Invalid key '" + key + "' specified to getCacheFile.");
        }
//        key = key.replaceAll("\\", "/");

        char[] chars = key.toCharArray();

        StringBuffer sb = new StringBuffer(chars.length + 8);

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            int pos = CHARS_TO_CONVERT.indexOf(c);
 
            if (pos >= 0) {
                sb.append('_');
                sb.append(pos);
            } else {
                sb.append(c);
            }
        }

        char[] fileChars = new char[sb.length()];
        sb.getChars(0, fileChars.length, fileChars, 0);
        return fileChars;
    }
    
    
    
    
    

    /**
     * 清除缓存 groupName 为CacheURL.oid
     * @param groupName  
     */
    @Override
	public void removeGroup(String groupName) throws CachePersistenceException {
    	Set group = retrieveGroup(groupName);
    	if(group==null) return;
    	Iterator iter = group.iterator();
    	//删除 group 下cache
		while(iter.hasNext()){
			String key = iter.next().toString();
			remove(key);
		}
		//删除 cacheGroupFile
		File file = getCacheGroupFile(groupName);
        remove(file);
	}


    /**
     * Builds a fully qualified file name that specifies a cache group entry.
     *
     * @param group The name of the group
     * @return A File reference
     */
    private File getCacheGroupFile(String group) {
        int AVERAGE_PATH_LENGTH = 30;

        if ((group == null) || (group.length() == 0)) {
            throw new IllegalArgumentException("Invalid group '" + group + "' specified to getCacheGroupFile.");
        }

        StringBuffer path = new StringBuffer(AVERAGE_PATH_LENGTH);

        // Build a fully qualified file name for this group
        path.append(GROUP_DIRECTORY).append('/');
        path.append(getCacheFileName(group)).append('.').append(CACHE_EXTENSION);

        return new File(getRoot(), path.toString());
    }


}
