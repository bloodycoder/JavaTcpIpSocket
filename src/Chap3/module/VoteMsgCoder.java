package Chap3.module;

import java.io.IOException;

public interface VoteMsgCoder {
    byte[] toWire(VoteMsg msg)throws IOException;
    VoteMsg fromWire(byte[] input)throws  IOException;
}
