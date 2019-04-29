package ajou.com.skechip.Fragment.bean;

import java.io.Serializable;
//import com.google.gson.annotations.SerializedName;

public class GroupEntity implements Serializable{

    public GroupEntity(String groupTitle, int groupImg){
        this.groupTitle = groupTitle;
        this.groupImg = groupImg;
    }

    private String groupTitle;
    private int groupImg;

    public String getGroupTitle() {
        return groupTitle;
    }

    public int getGroupImg() {
        return groupImg;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public void setGroupImg(int groupImg) {
        this.groupImg = groupImg;
    }
}

//
//public class RoomContext implements Serializable {
//
//    public RoomContext (){
//
//    }
//
//    public RoomContext(String room_name, Vote vote){
//        this.room_name = room_name;
//        this.vote = vote;
//    }
//
//    @SerializedName("room_name")
//    private String room_name;
//
//    @SerializedName("vote")
//    private Vote vote;
//
//    @SerializedName("rtmp_url")
//    private String streaming_url;
//
//    @SerializedName("xmpp_host")
//    private String XMPPHost;
//
//    @SerializedName("xmpp_service_name")
//    private String XMPPService;
//
//    @SerializedName("xmpp_room_name")
//    private String XMPPRoomName;
//
//    @SerializedName("room_idx")
//    private String roomId;
//
//    //for sqlLite
//    private int id;
//
//    public String getRoomId() { return roomId; }
//
//    public String getXMPPHost() { return XMPPHost; }
//
//    public String getXMPPService() { return XMPPService; }
//
//    public String getXMPPRoomName() { return XMPPRoomName; }
//
//    public String getStreaming_url() {
//        return streaming_url;
//    }
//
//    public Vote getVote() {
//        return vote;
//    }
//
//    public void setVote(Vote vote) {
//        this.vote = vote;
//    }
//
//    public String getRoom_name() {
//        return room_name;
//    }
//
//    public void setRoom_name(String room_name) {
//        this.room_name = room_name;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public void setRoomId(String roomId) {
//        this.roomId = roomId;
//    }
//
//}