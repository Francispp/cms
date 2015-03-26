package com.cyberway.common.quartz.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.quartz.impl.jdbcjobstore.StdJDBCDelegate;
import org.slf4j.Logger;

public class JDBCDelegate extends StdJDBCDelegate {

	public JDBCDelegate(Logger log, String tablePrefix, String instanceId) { 
		super(log, tablePrefix, instanceId); 
		} 

		public JDBCDelegate(Logger log, String tablePrefix, String instanceId, Boolean useProperties) { 
		super(log, tablePrefix, instanceId, useProperties); 
		} 
	protected Object getJobDetailFromBlob(ResultSet rs, String colName)
			throws ClassNotFoundException, IOException, SQLException {

		if (canUseProperties()) {
			Blob blobLocator = rs.getBlob(colName);
			if ((blobLocator != null) && (rs.wasNull() == false)) {
				InputStream binaryInput = blobLocator.getBinaryStream();
				return binaryInput;
			} else {
				return null;
			}
		}

		return getObjectFromBlob(rs, colName);
	}

	protected Object getObjectFromBlob(ResultSet rs, String colName)
			throws ClassNotFoundException, IOException, SQLException {
		Object obj = null;

		Blob blobLocator = rs.getBlob(colName);
		if ((blobLocator != null) && (rs.wasNull() == false)) {
			InputStream binaryInput = blobLocator.getBinaryStream();

			if (null != binaryInput) {
				ObjectInputStream in = new ObjectInputStream(binaryInput);
				obj = in.readObject();
				in.close();
				binaryInput.close();
			}
		}
		return obj;
	}
	
	/*public int insertTrigger(Connection conn, Trigger trigger, String state,
            JobDetail jobDetail) throws SQLException, IOException {
 
        ByteArrayOutputStream baos = null;
        if(trigger.getJobDataMap().size() > 0)
            baos = serializeJobData(trigger.getJobDataMap());
        
        PreparedStatement ps = null;
 
        int insertResult = 0;
 
        try {
            ps = conn.prepareStatement(rtp(INSERT_TRIGGER));
            ps.setString(1, trigger.getName());
            ps.setString(2, trigger.getGroup());
            ps.setString(3, trigger.getJobName());
            ps.setString(4, trigger.getJobGroup());
            ps.setBoolean(5, trigger.isVolatile());
            ps.setString(6, trigger.getDescription());
            ps.setBigDecimal(7, new BigDecimal(String.valueOf(trigger
                    .getNextFireTime().getTime())));
            long prevFireTime = -1;
            if (trigger.getPreviousFireTime() != null) {
                prevFireTime = trigger.getPreviousFireTime().getTime();
            }
            ps.setBigDecimal(8, new BigDecimal(String.valueOf(prevFireTime)));
            ps.setString(9, state);
            if (trigger instanceof SimpleTrigger) {
                ps.setString(10, TTYPE_SIMPLE);
            } else if (trigger instanceof CronTrigger) {
                ps.setString(10, TTYPE_CRON);
            } else { // (trigger instanceof BlobTrigger)
                ps.setString(10, TTYPE_BLOB);
            }
            ps.setBigDecimal(11, new BigDecimal(String.valueOf(trigger
                    .getStartTime().getTime())));
            long endTime = 0;
            if (trigger.getEndTime() != null) {
                endTime = trigger.getEndTime().getTime();
            }
            ps.setBigDecimal(12, new BigDecimal(String.valueOf(endTime)));
            ps.setString(13, trigger.getCalendarName());
            ps.setInt(14, trigger.getMisfireInstruction());
            if(baos != null)
                ps.setBytes(15, baos.toByteArray());
            else
                ps.setBytes(15, new byte[0]); // This is the change
            
            insertResult = ps.executeUpdate();
        } finally {
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException ignore) {
                }
            }
        }
 
        if (insertResult > 0) {
            String[] trigListeners = trigger.getTriggerListenerNames();
            for (int i = 0; trigListeners != null && i < trigListeners.length; i++)
                insertTriggerListener(conn, trigger, trigListeners[i]);
        }
 
        return insertResult;
    }*/



}
