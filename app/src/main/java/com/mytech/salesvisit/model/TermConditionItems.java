package com.mytech.salesvisit.model;

public class TermConditionItems {
    int TermId;

    public int getTermId() {
        return TermId;
    }

    public void setTermId(int termId) {
        TermId = termId;
    }

    public int getSrNo() {
        return SrNo;
    }

    public void setSrNo(int srNo) {
        SrNo = srNo;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getParticularId() {
        return ParticularId;
    }

    public void setParticularId(int particularId) {
        ParticularId = particularId;
    }

    public String getParticularName() {
        return ParticularName;
    }

    public void setParticularName(String particularName) {
        ParticularName = particularName;
    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String condition) {
        Condition = condition;
    }

    public boolean isRemoved() {
        return IsRemoved;
    }

    public void setRemoved(boolean removed) {
        IsRemoved = removed;
    }

    int SrNo;
    int OrderId;
    int ParticularId;
    String ParticularName;
    String Condition;
    boolean IsRemoved;
}


