package Chap3.module;

public class VoteMsg {
    private boolean isInquiry;
    private boolean isResponse;
    private int candidateID;
    private long voteCount;
    public static final int MAX_CANDIDATE_ID = 1000;
    public VoteMsg(boolean isResponse,boolean isInquiry,int candidateID,long voteCount){
        this.candidateID = candidateID;
        this.isInquiry  = isInquiry;
        this.isResponse = isResponse;
        this.voteCount = voteCount;
    }
    public void setInquiry(boolean isInquiry){
        this.isInquiry = isInquiry;
    }
    public void setResponse(boolean isResponse){
        this.isResponse = isResponse;
    }
    public boolean isInquiry(){
        return isInquiry;
    }
    public boolean isResponse(){
        return isResponse;
    }
    public void setCandidateID(int candidateID){
        this.candidateID = candidateID;
    }
    public int getCandidateID(){
        return candidateID;
    }
    public void setVoteCount(long count){
        voteCount = count;
    }
    public long getVoteCount(){
        return voteCount;
    }
    public String toString(){
        String res = (isInquiry?"inquiry":"vote")+"for candidate "+candidateID;
        if(isResponse){
            res = "response to "+res+" who now has "+voteCount+" vote(s) ";
        }
        return res;
    }
}
