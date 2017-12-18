package com.piar.entity;

import org.jboss.netty.buffer.ChannelBuffer;

/**
 * Created by jnkmhbl on 17/3/8.
 */
public class CodeEvent  {
    private ChannelBuffer buffer ;
    private long receiveTime ;
    private boolean crc32Checked ; //是否已经进行数据完整性校验
    private boolean valid ; //是否校验通过
    private boolean unified ;//是否已经加入循环校验码
    private String remoteAddress ;




    public boolean isUnified() {
        return unified;
    }

    public void setUnified(boolean unified) {
        this.unified = unified;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isCrc32Checked() {
        return crc32Checked;
    }

    public void setCrc32Checked(boolean crc32Checked) {
        this.crc32Checked = crc32Checked;
    }

    public ChannelBuffer getBuffer() {
        return buffer;
    }

    public void setBuffer(ChannelBuffer buffer) {
        this.buffer = buffer;
    }

    public long getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(long receiveTime) {
        this.receiveTime = receiveTime;
    }
}
