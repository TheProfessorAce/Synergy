package appwarp;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.cookies.synergy.game.utils.constants;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.AllRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LobbyEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.MatchedRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;

public class WarpController {

	private static WarpController instance;
	
	private final String apiKey = constants.appKey;
	private final String secretKey = constants.secretKey;
	
	private WarpClient warpClient;
	
	private String localUser;
	private String roomId;
	
	private boolean isConnected = false;
	private boolean lobby = false;
	boolean isUDPEnabled = false;
	
	private WarpListener warpListener ;
	
	private int STATE;
    public static boolean CONNECTED = false;
    public static boolean START = false;
    public static boolean roomsReady = false;

    public static List<String> RoomList = new ArrayList<String>();
	public static List<String> rooms = new ArrayList<String>();
    public static List<String> roomName = new ArrayList<String>();
    public static List<String> roomCreator = new ArrayList<String>();
    public static List<String> maxPlayer = new ArrayList<String>();

    public static int ROOMS = 0;
	
	// Game state constants
	public static final int WAITING = 1;
	public static final int STARTED = 2;
	public static final int COMPLETED = 3;
	public static final int FINISHED = 4;
	
	// Game completed constants
	public static final int GAME_WIN = 5;
	public static final int GAME_LOOSE = 6;
	public static final int ENEMY_LEFT = 7;
	
	public WarpController() {
		initAppwarp();
        initList();
		warpClient.addConnectionRequestListener(new ConnectionListener(this));
		warpClient.addChatRequestListener(new ChatListener(this));
		warpClient.addZoneRequestListener(new ZoneListener(this));
		warpClient.addRoomRequestListener(new RoomListener(this));
		warpClient.addNotificationListener(new NotificationListener(this));
        warpClient.addLobbyRequestListener(new LobbyListener(this));
	}

    private void initList() {
        rooms.add(" ");
    }
	
	public static WarpController getInstance(){
		if(instance == null){
			instance = new WarpController();
		}
		return instance;
	}

    public int getConnectionState() {
        return warpClient.getConnectionState();
    }
	
	public void startApp(String localUser){
		this.localUser = localUser;
        if(!constants.prefs.contains("sessionid")) {
            warpClient.connectWithUserName(localUser);
            System.out.println("Session ID: -1");
        }
        else {
            warpClient.RecoverConnectionWithSessionID(constants.prefs.getInteger("sessionid"), localUser);
            System.out.println("Session ID: " + constants.prefs.getInteger("sessionid"));
        }
	}

    public void getAllRooms() {
        warpClient.getAllRooms();
    }

    public void getLiveRoomInfo(String id) {
        warpClient.getLiveRoomInfo(id);
    }
	
	public void setListener(WarpListener listener){
		this.warpListener = listener;
	}
	
	public void stopApp(){
		if(isConnected){
			warpClient.unsubscribeRoom(roomId);
			warpClient.leaveRoom(roomId);
		}
		warpClient.disconnect();
	}
	
	private void initAppwarp(){
		try {
			WarpClient.initialize(apiKey, secretKey);
			warpClient = WarpClient.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendGameUpdate(String msg){
		if(isConnected){
			if(isUDPEnabled){
				warpClient.sendUDPUpdatePeers((localUser+"#@"+msg).getBytes());
			}else{
				warpClient.sendUpdatePeers((localUser+"#@"+msg).getBytes());
			}
		}
	}

	public void setJoinedLobby(boolean bool) {
		lobby = bool;
	}

	public boolean joinedLobby() {
		return lobby;
	}
	
	public void updateResult(int code, String msg){
		if(isConnected){
			STATE = COMPLETED;
			HashMap<String, Object> properties = new HashMap<String, Object>();
			properties.put("result", code);
			warpClient.lockProperties(properties);
		}
	}
	
	public void onConnectDone(boolean status){
		log("onConnectDone: "+status);
        CONNECTED = status;
		if(status){
            constants.prefs.putInteger("sessionid", warpClient.getSessionID());
			warpClient.initUDP();
			warpClient.joinLobby();
		}else{
			isConnected = false;
			handleError();
		}
	}
	
	public void onDisconnectDone(boolean status){
	}
	
	public void onRoomCreated(String roomId){
	}

    public void onJoinLobbyDone(LobbyEvent event) {
        if(event.getResult()==WarpResponseResultCode.SUCCESS){// success case
            warpClient.subscribeLobby();
        }
    }
	
	public void onJoinRoomDone(RoomEvent event){
		log("onJoinRoomDone: " + event.getResult());
		if(event.getResult()==WarpResponseResultCode.SUCCESS){// success case
			this.roomId = event.getData().getId();
			warpClient.subscribeRoom(roomId);
		}
		else{
			warpClient.disconnect();
			handleError();
		}
	}

    public void createRoom(String name, String creator, int maxPlayer) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("name", name);
        data.put("creator", creator);
        data.put("maxPlayer", maxPlayer);
        warpClient.createRoom(name, creator, maxPlayer, data);
    }
	
	public void onRoomSubscribed(String roomId){
		log("onSubscribeRoomDone: " + roomId);
		if(roomId!=null){
			isConnected = true;
			warpClient.getLiveRoomInfo(roomId);
		}else{
			warpClient.disconnect();
			handleError();
		}
	}
	
	public void onGetLiveRoomInfo(LiveRoomInfoEvent e){
        if(!roomName.contains(e.getProperties().get("name").toString())) {
            roomName.add(e.getProperties().get("name").toString());
            roomCreator.add(e.getProperties().get("creator").toString());
            maxPlayer.add(e.getProperties().get("maxPlayer").toString());
			log("Nakasulod ko!");
        }
        log("WHYY");
        log(e.getProperties().get("name").toString() + " " + e.getProperties().get("creator").toString() + " " + e.getProperties().get("maxPlayer").toString());
        for(int x=0;x<roomName.size();x++) {
            RoomList.add(" ");
            RoomList.set(x, rooms.get(x) + "\t" + roomName.get(x) + "\t" + roomCreator.get(x) + "\t" +maxPlayer.get(x));
        }
        roomsReady = true;
	}
	
	public void onUserJoinedRoom(String roomId, String userName){
		/*
		 * if room id is same and username is different then start the game
		 */
		if(localUser.equals(userName)==false){
            //TODO: Change the condition to an update from other player ready
			startGame();
		}
	}

    public void onGetAllRooms(AllRoomsEvent e) {
		if(e.getRoomIds() != null) {
            rooms = Arrays.asList(e.getRoomIds());
            for(int x=0;x<rooms.size();x++) {
                warpClient.getLiveRoomInfo(rooms.get(x));
            }
        }
    }

	public void onSendChatDone(boolean status){
		log("onSendChatDone: "+status);
	}
	
	public void onGameUpdateReceived(String message){
//		log("onMoveUpdateReceived: message"+ message );
		String userName = message.substring(0, message.indexOf("#@"));
		String data = message.substring(message.indexOf("#@")+2, message.length());
		if(!localUser.equals(userName)){
			warpListener.onGameUpdateReceived(data);
		}
	}
	
	public void onResultUpdateReceived(String userName, int code){
		if(localUser.equals(userName)==false){
			STATE = FINISHED;
			warpListener.onGameFinished(code, true);
		}else{
			warpListener.onGameFinished(code, false);
		}
	}
	
	public void onUserLeftRoom(String roomId, String userName){
		log("onUserLeftRoom "+userName+" in room "+roomId);
		if(STATE==STARTED && !localUser.equals(userName)){// Game Started and other user left the room
			warpListener.onGameFinished(ENEMY_LEFT, true);
		}
	}
	
	public int getState(){
		return this.STATE;
	}
	
	private void log(String message){
		if(constants.showLog){
			System.out.println(message);
		}
	}
	
	private void startGame(){
		STATE = STARTED;
		warpListener.onGameStarted("Start the Game");
	}
	
	private void waitForOtherUser(){
		STATE = WAITING;
		warpListener.onWaitingStarted("Waiting for other user");
	}
	
	private void handleError(){
		warpListener.onError("Error");
		if(roomId!=null && roomId.length()>0){
			warpClient.deleteRoom(roomId);
		}
		disconnect();
	}
	
	public void handleLeave(){
		if(isConnected){
			warpClient.unsubscribeRoom(roomId);
			warpClient.leaveRoom(roomId);
			if(STATE!=STARTED){
				warpClient.deleteRoom(roomId);
			}
			warpClient.disconnect();
		}
	}
	
	private void disconnect(){
		warpClient.removeConnectionRequestListener(new ConnectionListener(this));
		warpClient.removeChatRequestListener(new ChatListener(this));
		warpClient.removeZoneRequestListener(new ZoneListener(this));
		warpClient.removeRoomRequestListener(new RoomListener(this));
		warpClient.removeNotificationListener(new NotificationListener(this));
		warpClient.disconnect();
	}
}
